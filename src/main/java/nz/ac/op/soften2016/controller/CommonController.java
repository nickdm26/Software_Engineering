package nz.ac.op.soften2016.controller;
import nz.ac.op.soften2016.bean.Pizza;
import nz.ac.op.soften2016.bean.PizzaOrder;
import nz.ac.op.soften2016.bean.PizzaTopping;
import nz.ac.op.soften2016.bean.Topping;
import nz.ac.op.soften2016.bean.XUser;
import nz.ac.op.soften2016.security.Utilities;
import nz.ac.op.soften2016.bean.XOrder;
import nz.ac.op.soften2016.service.Dao;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import nz.ac.op.soften2016.service.QRCodeGeneration;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.noggit.JSONParser;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import net.sf.json.JSONObject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class CommonController {

    @Autowired
    ServletContext servletContext;
    private final String MAIN_TEMPLATE = "main";
    private DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Date date = new Date();

    @Autowired
    private Dao dao;
    private final static Logger log = LoggerFactory.getLogger(CommonController.class);
    /**
     * @param pizza
     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView defaultPage(HttpServletRequest request) {
        Map model = Utilities.SetUp(new HashMap());
        model.put("page", "home");

        return new ModelAndView(MAIN_TEMPLATE, model);
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView homePage(HttpServletRequest request){
        Map model = Utilities.SetUp(new HashMap());
        model.put("page","home");
        return new ModelAndView(MAIN_TEMPLATE,model);
    }
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contactPage(HttpServletRequest request){
        Map model = Utilities.SetUp(new HashMap());
        model.put("page","contact");
        return new ModelAndView(MAIN_TEMPLATE,model);
    }

    @RequestMapping(value = "/ShowPizza/{id}", method = RequestMethod.GET)
    public ModelAndView ShowPizzaPage(HttpServletRequest request, @PathVariable("id") Long id) {

        Criterion critScannerOrder = Restrictions.eq("scannerOrder", true);

        List<XOrder> orderList = dao.search(XOrder.class, critScannerOrder);

        Map model = Utilities.SetUp(new HashMap());
        Pizza GottenPizza = (Pizza) dao.get(Pizza.class, id);
        XOrder newOrder = new XOrder(false, true);
        if(GottenPizza != null){
            if(orderList.size() == 1){
                newOrder = orderList.get(0);
            }else if(orderList.size() > 1){
                //To Do
            }
            newOrder.createNewPizzaOrder(GottenPizza, 1);
            newOrder.setOrderDate(formatter.format(date));
            
            dao.save(newOrder);
            model.put("GottenPizza", GottenPizza);
            model.put("Order", newOrder);
        }else if(orderList.size() == 1){
            newOrder = orderList.get(0);
            model.put("Order", newOrder);
        }
        //model.put("id", id);
        model.put("page", "ShowPizza");
        return new ModelAndView(MAIN_TEMPLATE, model);
    }

    @RequestMapping(value = "/deletePizzaFromOrder", method = RequestMethod.POST)
    public String deleteTopping(HttpServletRequest request, @ModelAttribute("pizzaorder") PizzaOrder pizzaorder){
        Map model = Utilities.SetUp(new HashMap());

        dao.delete(pizzaorder);
    	/*
    	Criterion critScannerOrder = Restrictions.eq("scannerOrder", true);
		List<XOrder> orderList = dao.search(XOrder.class, critScannerOrder);

		XOrder newOrder = new XOrder(false, true);
		if(orderList.size() == 1){
			newOrder = orderList.get(0);
			newOrder.deletePizzaFromOrder(pizzaorder);
		}else if(orderList.size() > 1){
			//To Do
		}

        dao.save(newOrder);
        */
        return "redirect:ShowPizza/0";
    }
}
