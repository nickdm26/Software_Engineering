package nz.ac.op.soften2016.controller;

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import java.util.*;


@Controller
public class AdminController {
    ServletContext servletContext;
    private final String MAIN_TEMPLATE = "main";

    @Autowired
    private Dao dao;
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    private final static Logger log = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error) {
        {
            List<User> users = dao.list(User.class);
            Map model = new HashMap();
            if (users.size() == 0) {
                User user = new User();
                user.setUsername("root");
                String password = "P@ssw0rd";
                user.setSalt(Utilities.CreateSalt());
                String encryptedPassword = Utilities.EncryptedPassword(password, user.getSalt());
                user.setPassword(encryptedPassword);
                dao.save(user);
                Utilities.CheckProviders();
            }
            if (error != null) {
                model.put("error", "Invalid username and password!");
            }
            model.put("page", "login");
            return new ModelAndView(MAIN_TEMPLATE, model);
        }
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

/*
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPage(@ModelAttribute("login") User loginDetails) {
        List<User> users = dao.list(User.class);
        Map model = new HashMap();
        for (User user : users) {
            if (user.getUsername().equals(loginDetails.getUsername())) {
                if (Utilities.PasswordCheck(user.getPassword(), user.getSalt(), loginDetails.getPassword())) {
                    model.put("page", "admin");
                    return new ModelAndView(MAIN_TEMPLATE, model);
                }
            }
        }
        model.put("error", "Invalid Login Credentials");
        model.put("page", "login");
        return new ModelAndView(MAIN_TEMPLATE, model);
    }
*/

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage(HttpServletRequest request) {

        List<Topping> toppings = dao.list(Topping.class);
        List<Pizza> pizzas = dao.list(Pizza.class);

        Map model = Utilities.SetUp(new HashMap());
        model.put("page", "admin");
        model.put("toppings", toppings);
        model.put("pizzas", pizzas);
        return new ModelAndView(MAIN_TEMPLATE, model);
    }

    @RequestMapping(value = "/adminDeleteUpdate", method = RequestMethod.GET)
    public ModelAndView adminDeleteUpdatePage(HttpServletRequest request) {
    	Map model = Utilities.SetUp(new HashMap());
        List<Topping> toppings = dao.list(Topping.class);

        model.put("toppings", toppings);

        model.put("page", "adminDeleteUpdate");
        return new ModelAndView(MAIN_TEMPLATE, model);
    }
    
  //===================================================================================================================================//
    //26/10/16 - Don't Delete!!
    @RequestMapping(value = "/adminCreate", method = RequestMethod.GET)
    public ModelAndView adminCreatePage(HttpServletRequest request){
    	
    	List<Topping> toppings = dao.list(Topping.class);
    	//Collections.sort(toppings);
    	
    	Map model = Utilities.SetUp(new HashMap());
        model.put("page","adminCreate");
        model.put("toppings", toppings);
        return new ModelAndView(MAIN_TEMPLATE,model);
    }
//=====================================================================================================================================// 

  //=====================================================================================================================================//
    //26/10/16 - Don't Delete!!
    @RequestMapping(value = "/adminAddStock", method = RequestMethod.GET)
    public ModelAndView addStockPage(HttpServletRequest request)
    {

    	Map model = Utilities.SetUp(new HashMap());
        List<Pizza> pizzas = dao.list(Pizza.class);
        
        List<Topping> toppings = dao.list(Topping.class);
        
        model.put("pizzas", pizzas);
        model.put("toppings", toppings);
        model.put("page","adminAddStock");
        return new ModelAndView(MAIN_TEMPLATE,model);    
     }
  //==================================================================================================================================//
 

  //==================================================================================================================================//
    //Adding topping stock to the database
      @RequestMapping(value = "/addToppingStock", method = RequestMethod.POST)
      public String addToppingStock
      						(
      						  HttpServletRequest request,				//Grabbing information from the form
      						  @RequestParam String formData,
      						  ModelMap model
      						)
      {
      	System.out.println(formData);
      	Gson gson = new Gson();						//Create a Gson instance
      	JsonParser parser = new JsonParser();		//Create a Json parser
      	 
      	 JsonObject[] myObjs = gson.fromJson(parser.parse(formData), JsonObject[].class);	//Create an array of JsonObjects
      	 for(JsonObject o : myObjs)
      	 {
      		 String name = o.get("name").toString();					//Grab objects name and parse to a string. Name is the ID number of that topping
      		 String stripedName = name.replaceAll("\"", "");			//remove all back slash + double quote from the string
      		 System.out.println("Striped Name: " + stripedName);
      		 String value = o.get("value").toString();					//Grab the value: the number of topping to add to the database
      		 String stripedValue = value.replaceAll("\"", "");			//remove all back slash + double quote from the value string
      		 System.out.println("Striped Value: " + stripedValue);
      		 int quantity = Integer.parseInt(stripedValue);				//Parse value string to an integer
      		 Topping tempTopping = (Topping) dao.findOneSelection(Topping.class, stripedName);		//Make a temp topping that points to the selected topping in our database
       		if(tempTopping != null)
       		{
         	   		int currentQuantity = tempTopping.getQuantity();				//Grab the current quantity
         	   		System.out.println("Current Quantity: " + currentQuantity);
         	   		int newQuantity = currentQuantity + quantity;					//Add the current to the input quantity
         	   		System.out.println("New Quantity: " + newQuantity);
       			tempTopping.setQuantity(newQuantity);							//Set the new quantity
       			dao.update(tempTopping);
       		}
      	 }     	
      	
      	
      	Map model2 = Utilities.SetUp(new HashMap());
          model2.put("page","adminAddStock");
          String redirectUrl = "adminAddStock";
          return "redirect:" + redirectUrl;
      }
      
      //================================================================================================================================//

    //================================================================================================================================//
      //Adding pizza stock to the database
    //Adding pizza stock to the database
      @RequestMapping(value = "/addPizzaStock", method = RequestMethod.POST)
      public String addPizzaStock
                            (
                              HttpServletRequest request,               //Grabbing information from the form
                              @RequestParam String formData,
                              ModelMap model
                            )
      {
        System.out.println(formData);
        Gson gson = new Gson();                     //Create a Gson instance
        JsonParser parser = new JsonParser();       //Create a Json parser

         JsonObject[] myObjs = gson.fromJson(parser.parse(formData), JsonObject[].class);   //Create an array of JsonObjects
         for(JsonObject o : myObjs)
         {
             String id = o.get("pizzaID").toString();         //Grab and strip pizza ID
             String stripedID = id.replaceAll("\"", "");
             System.out.println("PizzaID: " + stripedID);

             String quantityAdded = o.get("quantityAdded").toString();                //Grab and strip number of pizzas to be added
             String stripedValue = quantityAdded.replaceAll("\"", "");
             System.out.println("Striped Quantity: " + stripedValue);
             int quantity = Integer.parseInt(stripedValue);                         //Parse quantity to an integer

             Pizza tempPizza = (Pizza) dao.findOneSelection(Pizza.class, stripedID);    //Find pizza in database

            JsonObject[] myTops = gson.fromJson(parser.parse(o.get("toppings").toString()), JsonObject[].class);

            for(JsonObject a : myTops)
             {
                 System.out.println(a.get("toppingID"));

                 String idTops = a.get("toppingID").toString();           //Grab and strip topping ID
                 String stripedTopsID = idTops.replaceAll("\"", "");
                 System.out.println("ToppingID: " + stripedTopsID);

                 String toppingQuantity = a.get("toppingQuantity").toString();                //Grab and strip number of toppings per pizza
                 String stripedQuantity = toppingQuantity.replaceAll("\"", "");
                 int quantityTops = Integer.parseInt(stripedQuantity);
                 System.out.println("Topping Quantity: " + quantityTops);

                 int totalTopsToBeRemoved = quantityTops * quantity;

                 Topping tempTopping = (Topping) dao.findOneSelection(Topping.class, stripedTopsID);    //Find topping in database

                 int currentQuan = tempTopping.getQuantity();           //Gets the current quantity of the above topping
                 int newQuantity = currentQuan - totalTopsToBeRemoved;  //Checks to see if there is enough toppings in the database
                 if (newQuantity <= 0)
                 {
                     //Do nothing
                 }
                 else
                 {
                     tempTopping.setQuantity(newQuantity);  //Reduces the number of toppings by the amount the pizza requires
                     dao.update(tempTopping);
                 }
             }

            if(tempPizza != null)
            {
                    int currentQuantity = tempPizza.getQuantity();
                    System.out.println("Current Quantity: " + currentQuantity);
                    int newQuantity = currentQuantity + quantity;
                    System.out.println("New Quantity: " + newQuantity);
                tempPizza.setQuantity(newQuantity);
                dao.update(tempPizza);
            }
             //System.out.println(o);
         }


        Map model2 = Utilities.SetUp(new HashMap());
          model2.put("page","adminAddStock");
          String redirectUrl = "adminAddStock";
          return "redirect:" + redirectUrl;
      }
      //========================================================================================================================================//
      
    //========================================================================================================================================//
      //Don't Delete!! 26/10/16
      @RequestMapping(value = "/createPizza", method = RequestMethod.POST)
      public String createPizza
      						(
      						  HttpServletRequest request,				//Grabbing information from the form
      						  @RequestParam String formData,
      						  ModelMap model
      						)
      {
      	
      	System.out.println(formData);
      	Gson gson = new Gson();						//Create a Gson instance
      	JsonParser parser = new JsonParser();		//Create a Json parser
      	System.out.println(formData);
      	 JsonObject myObjs = gson.fromJson(parser.parse(formData), JsonObject.class);	//Create an array of JsonObjects
      	 
      	 System.out.println(myObjs.get("pizzaName"));
      	 System.out.println(myObjs.get("pizzaSize"));
      	 System.out.println(myObjs.get("pizzaSpicy"));
      	 System.out.println(myObjs.get("toppings"));
      	 
      	 String pizzaName = myObjs.get("pizzaName").toString();
  		 String stripedName = pizzaName.replaceAll("\"", "");			//Grab and pizza name
  		 System.out.println("Stripped Name: " + stripedName);
  		 
  		 String pizzaSize = myObjs.get("pizzaSize").toString();
  		 String stripedSize = pizzaSize.replaceAll("\"", "");			//Grab pizza size
  		 System.out.println("Stripped Value: " + stripedSize);
  		 
  		 String pizzaSpicy = myObjs.get("pizzaSpicy").toString();
  		 String stripedSpicy = pizzaSpicy.replaceAll("\"", "");			//Grab pizza spicy
  		 System.out.println("Stripped Value: " + stripedSpicy);
  		 
      	 
      	 Pizza createPizza = new Pizza();								//Create A New Pizza
       		createPizza.setName(stripedName);	//Set Pizza Name
       		createPizza.setSize(stripedSize);	//Set Pizza Size
       		Boolean spicy = false;

          System.out.println(spicy);
       		if(stripedSpicy.contentEquals("spicy"))
       		{
       			spicy = true;
       		}
       		createPizza.setSpicy(spicy);  								//Set spicy Boolean
       		createPizza.setQuantity(0);									//Set Pizza Quantity

       	 switch(stripedSize)
  		 {
  		 case "Small":
  			 createPizza.setPrice(6.00);
  			 break;
  		 case "Medium":
  			createPizza.setPrice(9.00);
 			 break;
  		 case "Large":
  			createPizza.setPrice(14.00);
 			 break;
  		 default:
  			createPizza.setPrice(100.00);
			 break;
  		 }


      	 JsonObject[] myTops = gson.fromJson(parser.parse(myObjs.get("toppings").toString()), JsonObject[].class);
      	 
      	 for(JsonObject a : myTops)
      	 {    		
      		 System.out.println(a.get("toppingID"));
      		 System.out.println(a.get("toppingQuantity"));
      		 
      		 String topID = a.get("toppingID").toString();
      		 String stripedTopID = topID.replaceAll("\"", "");			//Remove quotes from topID
      		 
      		 String topQuantity = a.get("toppingQuantity").toString();
      		 String stripedTopQuantity = topQuantity.replaceAll("\"", "");			//Remove quotes from topQuantity
      		 
      		 Topping tempTopping = (Topping) dao.findOneSelection(Topping.class, stripedTopID);
      		 int numToppings = Integer.parseInt(stripedTopQuantity);
      		 if(tempTopping != null)
        		{
          	   		createPizza.createPizzaTopping(tempTopping, numToppings);
        		}
      	 }
       	
       	dao.save(createPizza);
      	
      	
       Map model2 = Utilities.SetUp(new HashMap());
          model2.put("page","order");
          String redirectUrl = "order";
          return "redirect:" + redirectUrl;        
      }
      
      //===================================================================================================================================================//


    //===================================================================================================================================================//
      //Creates a topping in the database
      //26/10/16 - Don't Delete!!
      @RequestMapping(value = "/createTopping", method = RequestMethod.POST)
      public String createTopping
      						(
      						  HttpServletRequest request,				//Grabbing information from the form
      						  @RequestParam String name,
      						  ModelMap model
      						)
      {
      	Topping createTopping = new Topping();
      	createTopping.setName(name);
      	createTopping.setQuantity(0);
      	dao.save(createTopping);
      	
      	Map model2 = Utilities.SetUp(new HashMap());
          model2.put("page","adminCreate");
          String redirectUrl = "adminCreate";
          return "redirect:" + redirectUrl;
          
          //return new ModelAndView(MAIN_TEMPLATE,model2);
      }
    //===================================================================================================================================================//

/*    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage(HttpServletRequest request) {
        List<Topping> toppings = dao.list(Topping.class);
        Map model = new HashMap();

        model.put("toppings", toppings);
        model.put("page", "admin");
        return new ModelAndView(MAIN_TEMPLATE, model);
    }*/



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView add(HttpServletRequest request, @ModelAttribute("pizza") Pizza pizza){
        dao.save(pizza);
        Map model = Utilities.SetUp(new HashMap());
        model.put("page","order");
        return new ModelAndView(MAIN_TEMPLATE,model);
    }

    //probably not using.......
    @RequestMapping(value = "/addTopping", method = RequestMethod.POST)
    public ModelAndView addTopping(HttpServletRequest request, @ModelAttribute("topping") Topping topping){
        dao.save(topping);
        Map model = Utilities.SetUp(new HashMap(new HashMap()));
        model.put("page","admin");
        return new ModelAndView(MAIN_TEMPLATE,model);
    }

    @RequestMapping(value = "/updateTopping", method = RequestMethod.POST)
    public String updateTopping(HttpServletRequest request, @ModelAttribute("topping") Topping topping){
        dao.update(topping);
        return "redirect:adminDeleteUpdate";
    }

    @RequestMapping(value = "/deleteTopping", method = RequestMethod.POST)
    public String deleteTopping(HttpServletRequest request, @ModelAttribute("topping") Topping topping){
		dao.delete(topping);
		return "redirect:adminDeleteUpdate";
    }
   /*RequestMapping(value = "/delpizza", method = RequestMethod.GET)
    public ModelAndView delPizzaPage(HttpServletRequest request) {
        List<Pizza> pizzas = dao.list(Pizza.class);
        Map model = new HashMap();

        model.put("pizzas", pizzas);
        model.put("page", "delpizza");
        return new ModelAndView(MAIN_TEMPLATE, model);
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public String delPizza(@ModelAttribute("delPizza") Pizza formPizza, String formToppings) {
        String[] pizzaNames = formToppings.split(",");
        List<Pizza> pizzas = dao.list(Pizza.class);
        for (Pizza pizza : pizzas) {
            for (String name : pizzaNames) {
                if (pizza.getName().contentEquals(name)) {
                    Set<PizzaTopping> pizzaTopping = pizza.getPizzaToppings();
                    dao.delete(pizza);
                }
            }
        }
        return "redirect:order.html";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPizza(@ModelAttribute("addPizza") Pizza pizza, String formToppings) {
        System.out.println(pizza.getName());
        String[] toppings = formToppings.split(",");
        List<Topping> databaseToppings = dao.list(Topping.class);
        for (Topping databaseTopping : databaseToppings) {
            for (String topping : toppings) {
                if (databaseTopping.getName().equalsIgnoreCase(topping)) {
                    PizzaTopping pizzaTopping = new PizzaTopping();
                    pizzaTopping.setTopping(databaseTopping);
                    pizza.addPizzaTopping(pizzaTopping);
                }
            }
        }
        if (pizza.getPizzaToppings() != null) {
            dao.save(pizza);
            return "redirect:order.html";
        }
        return "redirect:admin.html";
    }*/
      
    @RequestMapping(value = "/adminCurrentOrders", method = RequestMethod.GET)
    public ModelAndView contactPage(HttpServletRequest request){
    List<XOrder> orders = dao.list(XOrder.class);
    Map model = Utilities.SetUp(new HashMap());
	model.put("orders",orders);
    model.put("page","adminCurrentOrders");

    return new ModelAndView(MAIN_TEMPLATE,model);
    }
}
