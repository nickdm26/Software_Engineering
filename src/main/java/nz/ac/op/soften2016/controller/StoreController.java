package nz.ac.op.soften2016.controller;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import nz.ac.op.soften2016.bean.*;
import nz.ac.op.soften2016.security.Utilities;
import nz.ac.op.soften2016.service.Dao;
import nz.ac.op.soften2016.service.QRCodeGeneration;
import org.apache.noggit.JSONParser;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import net.sf.json.JSONObject;
import com.google.gson.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;

@Controller
public class StoreController
{
    ServletContext servletContext;
    private final String MAIN_TEMPLATE = "main";
    private final int REWARD = 20;

    @Autowired
    private Dao dao;
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Date date = new Date();
    private final static Logger log = LoggerFactory.getLogger(StoreController.class);
  
    // ----------------------------------------------------------------//
    //                    Creates 4 Pizzas into DB                     //
    //                      for testing purposes                       //
    // ----------------------------------------------------------------//
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public ModelAndView orderPage(HttpServletRequest request) {
        Map model = Utilities.SetUp(new HashMap());
        List <XOrder> orders = dao.list(XOrder.class);
        List <Pizza> pizzas = dao.list(Pizza.class);
        int totalSize = pizzas.size();
        
        // Setup initial 4 Pizzas

        if (pizzas.size() == 0) {
            // Captain Jack
            String[] cjList = {
                "Mozarella", "Salami", "Onion", "Green Peppers"
            };
            Pizza captainJack = new Pizza();
            captainJack.setName("Captain Jack");
            captainJack.setSize("Medium");
            captainJack.setSpicy(true);
            captainJack.setPrice(9.00);
            captainJack.setQuantity(2);
            for (String s: cjList) {
                Topping topping = new Topping();
                topping.setName(s);
                topping.setQuantity(1);
                dao.save(topping);
                captainJack.createPizzaTopping(topping, 3);
            }
            dao.save(captainJack);
            pizzas.add(captainJack); 
            
            // Billy the Kid 
            String[] btkList = {
                "Mozarella", "Cheddar", "Ham", "Mushrooms", "Tomato"
            };
            Pizza billyTheKid = new Pizza();
            billyTheKid.setName("Billy the Kid");
            billyTheKid.setSize("Large");
            billyTheKid.setPrice(14.00);
            billyTheKid.setSpicy(false);
            billyTheKid.setQuantity(4);
            for (String s: btkList) {
                Topping topping = new Topping();
                topping.setName(s);
                topping.setQuantity(1);
                dao.save(topping);
                billyTheKid.createPizzaTopping(topping, 3);
            }
            dao.save(billyTheKid);
            pizzas.add(billyTheKid);

            // Zanzibar   
            String[] zList = {
                "Pepperoni", "Cheddar", "Spinach", "Garlic"
            };
            Pizza zanzibar = new Pizza();
            zanzibar.setName("Zanzibar");
            zanzibar.setSize("Small");
            zanzibar.setSpicy(true);
            zanzibar.setPrice(6.00);
            zanzibar.setQuantity(2);
            for (String s: zList) {
                Topping topping = new Topping();
                topping.setName(s);
                topping.setQuantity(1);
                dao.save(topping);
                zanzibar.createPizzaTopping(topping, 3);
            }
            dao.save(zanzibar);
            pizzas.add(zanzibar);
 
            // Big Shot   
            String[] bsList = {
                "Pepperoni", "Cheddar", "Mozarella", "Salami", "Ham", "Chilli"
            };
            Pizza bigShot = new Pizza();
            bigShot.setName("Big Shot");
            bigShot.setSize("Large");
            bigShot.setSpicy(true);
            bigShot.setPrice(14.00);
            bigShot.setQuantity(7);
            for (String s: bsList) {
                Topping topping = new Topping();
                topping.setName(s);
                topping.setQuantity(1);
                dao.save(topping);
                bigShot.createPizzaTopping(topping, 3);
            }
            dao.save(bigShot);
            pizzas.add(bigShot);
        }
        model.put("pizzas", pizzas);
        model.put("orders", orders);
        model.put("page", "order");
        return new ModelAndView(MAIN_TEMPLATE, model);
    }  

    // ----------------------------------------------------------------//
    //                          Delete Order                           //
    //              Deletes Order from DB using pizzaId                //
    // ----------------------------------------------------------------//
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    public String deleteOrder(@RequestParam Long theOrderId, HttpServletRequest request)
    {
        Map model = Utilities.SetUp(new HashMap());

        // Get's the Order from the DB using the Order Id from the form
        XOrder theOrder = (XOrder)dao.get(XOrder.class, theOrderId);

        // Loop through the PizzaOrder set attached to the Order
        // deleting each Pizza
        for(PizzaOrder item : theOrder.getThePizzaOrders())
        {
            Pizza pizza = item.getPizza();
        } 
        dao.delete(theOrder);
           
        List <XOrder> orders = dao.list(XOrder.class);    
         
        model.put("orders", orders);         
        String redirectUrl = "adminCurrentOrders";
        return "redirect:" + redirectUrl;
    } 

		//----------------------------------------------------------------//
		//                          Create Order                           //
		//      Get form data as JsonObject and create new Order           //
		// ----------------------------------------------------------------// 
		@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
		public ResponseEntity<String> createOrder
		                        (
		                          HttpServletRequest request,       
		                          @RequestParam String newOrder,
		                          @RequestParam String orderDetails,
		                          ModelMap model
		                        )
		{            	
        Map map = Utilities.SetUp(new HashMap()); 
        List <XOrder> orders = dao.list(XOrder.class); 
        List <XUser> users = dao.list(XUser.class);   
            
        XOrder order = new XOrder(); 
        double orderTotal = 0;
        
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        String pizzaId="", pizzaName="", pizzasInStock="",numberOrdered="",pizzaPrice="",combinedPrice="";

        JsonObject[] orderObj = gson.fromJson(parser.parse(newOrder), JsonObject[].class);
        JsonObject detailObj = gson.fromJson(parser.parse(orderDetails), JsonObject.class);

        String firstName = detailObj.get("firstName").toString().replaceAll("\"","");
        String lastName = detailObj.get("lastName").toString().replaceAll("\"","");
        String address = detailObj.get("address").toString().replaceAll("\"","");
        String email = detailObj.get("email").toString().replaceAll("\"","");
        String typeOfOrder = detailObj.get("typeOfOrder").toString().replaceAll("\"","");
        
        // Checks if user exists based on email address
        Criterion checkEmail = Restrictions.eq("email", email);
        List <XUser> emailList = dao.search(XUser.class, checkEmail);

        if(emailList.size()>0)
        {            
            order.setUser(emailList.get(0));
        }
        else
        { 
            // Create new Customer (User)
            XUser user = new XUser(lastName,firstName, email); 
            dao.save(user);  
            order.setUser(user);  
        }  
         
        for (JsonObject a : orderObj) {
              
            pizzaId = a.get("pizzaId").toString().replaceAll("\"","");
            Long theId = Long.parseLong(pizzaId); 
            Pizza p = (Pizza) dao.get(Pizza.class, theId);
           
            if(p!=null)
            {               
                pizzasInStock = a.get("pizzasInStock").toString().replaceAll("\"","");
                numberOrdered = a.get("numberOrdered").toString().replaceAll("\"","");
                pizzaPrice = a.get("pizzaPrice").toString().replaceAll("\"","");
                combinedPrice = a.get("combinedPrice").toString().replaceAll("\"","");
                
                // Calculate updated quantity in stock
                int num = Integer.parseInt(numberOrdered);
                int newQuantity = Integer.parseInt(pizzasInStock)-num;
                p.setQuantity(newQuantity); 
                
                // Add to order total 
                orderTotal += Double.parseDouble(combinedPrice);
                
                order.createNewPizzaOrder(p, num); 
            }    
        }    
            
        order.setOrderCost(orderTotal);
        order.setOrderType(typeOfOrder);
        order.setAddress(address);
        order.setOrderDate(formatter.format(date));

        if(typeOfOrder.equals("Delivery"))
        {
            order.setOrderStatus("Order Complete");
            order.setOrderComplete(true);
        }
        else
        {
            order.setOrderStatus("Ready for pick-up");
            order.setOrderComplete(false);
        }   
        
        
         /* order.setAddress(address);
        order.setOrderDate(formatter.format(date)); // Change to @Transactional
        order.setTotalCost(orderTotal);
        order.setDelivery(deliver.equals("Delivery"));
        String status = order.isDelivery() ? "Awaiting delivery" : "Ready for pick-up";
        order.setStatus(status);*/
        
        
        //test me...................
        //Campbells rewards programme
        XUser currentUser = order.getUser();                        //get the current user
        currentUser.setTicks(currentUser.getTicks()+ Integer.parseInt(numberOrdered));      //set the current users ticks by adding quantity to the current amount of ticks
        if(currentUser.getTicks() >= 10)                         //if the users ticks is 10 
        {
            double rewardMultiplier = currentUser.getTicks() / 10;
            rewardMultiplier = (int) Math.floor(rewardMultiplier);
            currentUser.setTicks(currentUser.getTicks() % 10);
            
            double credit = REWARD * rewardMultiplier;      //calculate the users credit
             
            currentUser.setPizzasCredit(credit);            //set the users current credit
        }
        
        double newOrderValue = order.getOrderCost() - currentUser.getPizzasCredit();        //check me
        
        if(newOrderValue < 0)        //if the order value is less than the credit amount
        {
            currentUser.setPizzasCredit(currentUser.getPizzasCredit() - order.getOrderCost());      //subtract the order cost from the users current credit
            order.setOrderCost(0);      //the users order is free
        }
        else
        {
            order.setOrderCost(order.getOrderCost() - currentUser.getPizzasCredit());       //subtract the users credit from the order value
        }
        
        //test me ^^^^^^^^^^^^^^^^^^^^^^^^^^^         
                
        dao.save(order);  							// Save new order into DB       
        
        
        model.put("users", users); 
        model.put("orders", orders);  
        
        String thisId = order.getId().toString();	// Get this order ID		
       
        return new ResponseEntity<String>(thisId, new HttpHeaders(), HttpStatus.CREATED);
    } 
		
		@RequestMapping(value = "/SpecialOrder", method = RequestMethod.GET)
	    public ModelAndView SpecialOrderPage(HttpServletRequest request) {
	        Map model = Utilities.SetUp(new HashMap());
	        List <XOrder> orders = dao.list(XOrder.class);
	        List <Pizza> pizzas = dao.list(Pizza.class);
	        Criterion critScannerOrder = Restrictions.eq("scannerOrder", true);
	        List<XOrder> orderList = dao.search(XOrder.class, critScannerOrder);
	        XOrder newOrder = new XOrder(false, true);
	        
	        if(orderList.size() == 1){
	            newOrder = orderList.get(0);
	            model.put("Order", newOrder);
	        }
	        	        
	        model.put("pizzas", pizzas);
	        model.put("orders", orders);
	        model.put("page", "SpecialOrder");
	        return new ModelAndView(MAIN_TEMPLATE, model);
	    }
		
		@RequestMapping(value = "/CreateSpecialOrder", method = RequestMethod.POST)
		public ResponseEntity<String> CreateSpecialOrder
		                        (
		                          HttpServletRequest request,       
		                          @RequestParam String newOrder,
		                          @RequestParam String orderDetails,
		                          ModelMap model
		                        )
		{            	
        Map map = Utilities.SetUp(new HashMap()); 
        List <XOrder> orders = dao.list(XOrder.class); 
        List <XUser> users = dao.list(XUser.class);   
            
        XOrder order = new XOrder(); 
        double orderTotal = 0;
        
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        String pizzaId="", pizzaName="", pizzasInStock="",numberOrdered="",pizzaPrice="",combinedPrice="";

        JsonObject[] orderObj = gson.fromJson(parser.parse(newOrder), JsonObject[].class);
        JsonObject detailObj = gson.fromJson(parser.parse(orderDetails), JsonObject.class);

        String firstName = detailObj.get("firstName").toString().replaceAll("\"","");
        String lastName = detailObj.get("lastName").toString().replaceAll("\"","");
        String address = detailObj.get("address").toString().replaceAll("\"","");
        String email = detailObj.get("email").toString().replaceAll("\"","");
        String typeOfOrder = detailObj.get("typeOfOrder").toString().replaceAll("\"","");
        
        // Checks if user exists based on email address
        Criterion checkEmail = Restrictions.eq("email", email);
        List <XUser> emailList = dao.search(XUser.class, checkEmail);

        if(emailList.size()>0)
        {            
            order.setUser(emailList.get(0));
        }
        else
        { 
            // Create new Customer (User)
            XUser user = new XUser(lastName,firstName, email); 
            dao.save(user);  
            order.setUser(user);  
        }  
         
        for (JsonObject a : orderObj) {
              
            pizzaId = a.get("pizzaId").toString().replaceAll("\"","");
            Long theId = Long.parseLong(pizzaId); 
            Pizza p = (Pizza) dao.get(Pizza.class, theId);
           
            if(p!=null)
            {               
                pizzasInStock = a.get("pizzasInStock").toString().replaceAll("\"","");
                numberOrdered = a.get("numberOrdered").toString().replaceAll("\"","");
                pizzaPrice = a.get("pizzaPrice").toString().replaceAll("\"","");
                combinedPrice = a.get("combinedPrice").toString().replaceAll("\"","");
                
                // Calculate updated quantity in stock
                int num = Integer.parseInt(numberOrdered);
                int newQuantity = Integer.parseInt(pizzasInStock)-num;
                p.setQuantity(newQuantity); 
                
                // Add to order total 
                orderTotal += Double.parseDouble(combinedPrice);
                
                order.createNewPizzaOrder(p, num); 
            }    
        }    
            
        order.setOrderCost(orderTotal);
        order.setOrderType(typeOfOrder);
        order.setAddress(address);
        order.setOrderDate(formatter.format(date));

        if(typeOfOrder.equals("Delivery"))
        {
            order.setOrderStatus("Order Complete");
            order.setOrderComplete(true);
        }
        else
        {
            order.setOrderStatus("Ready for pick-up");
            order.setOrderComplete(false);
        }   
        
        
         /* order.setAddress(address);
        order.setOrderDate(formatter.format(date)); // Change to @Transactional
        order.setTotalCost(orderTotal);
        order.setDelivery(deliver.equals("Delivery"));
        String status = order.isDelivery() ? "Awaiting delivery" : "Ready for pick-up";
        order.setStatus(status);*/
        
        
        //test me...................
        //Campbells rewards programme
        XUser currentUser = order.getUser();                        //get the current user
        currentUser.setTicks(currentUser.getTicks()+ Integer.parseInt(numberOrdered));      //set the current users ticks by adding quantity to the current amount of ticks
        if(currentUser.getTicks() >= 10)                         //if the users ticks is 10 
        {
            double rewardMultiplier = currentUser.getTicks() / 10;
            rewardMultiplier = (int) Math.floor(rewardMultiplier);
            currentUser.setTicks(currentUser.getTicks() % 10);
            
            double credit = REWARD * rewardMultiplier;      //calculate the users credit
            
            currentUser.setPizzasCredit(credit);            //set the users current credit
        }
        
        double newOrderValue = order.getOrderCost() - currentUser.getPizzasCredit();        //check me
        
        if(newOrderValue < 0)        //if the order value is less than the credit amount
        {
            currentUser.setPizzasCredit(currentUser.getPizzasCredit() - order.getOrderCost());      //subtract the order cost from the users current credit
            order.setOrderCost(0);      //the users order is free
        }
        else
        {
            order.setOrderCost(order.getOrderCost() - currentUser.getPizzasCredit());       //subtract the users credit from the order value
        }
        

                
        dao.save(order);  							// Save new order into DB       
        
        model.put("users", users); 
        model.put("orders", orders);  
        
        String thisId = order.getId().toString();	// Get this order ID		
       
        return new ResponseEntity<String>(thisId, new HttpHeaders(), HttpStatus.CREATED);
    } 
        
    // ----------------------------------------------------------------//
    //                 Redirect to Order Confirmation Page             //
    //                      once order submitted                       //
    // ----------------------------------------------------------------//   
    @RequestMapping(value = "/orderComplete/{id}", method = RequestMethod.GET)
    public ModelAndView orderComplete(@PathVariable("id") Long id, HttpServletRequest request)
    { 
    	Map model = new HashMap();  
     
    	XOrder lastOrder = (XOrder) dao.findOneSelection(XOrder.class, id.toString());
    	
    	Set<PizzaOrder> orderPizzas = lastOrder.getThePizzaOrders();  
         
    	model.put("orderPizzas", orderPizzas);  
    	model.put("lastOrder", lastOrder);  
    	model.put("page", "orderComplete"); 
        return new ModelAndView(MAIN_TEMPLATE, model);
    }
}

