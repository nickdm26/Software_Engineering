package nz.ac.op.soften2016.bean;

import java.security.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by thoml on 24/08/2016.
 */

public class Order {

    private Long id;
    private String emailAddress;
    private Date dateTime;
    private String orderStatus;
    private double orderCost;
    private Set<PizzaOrder> pizzaOrderSet = new HashSet<PizzaOrder>();
    private Long[] pizzaOrderID;

    public Order(){}

    public  void addPizzaOrder(PizzaOrder pizzaOrder){

    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getEmailAddress() {return emailAddress;}
    public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}

    public Date getDateTime() {return dateTime;}
    public void setDateTime(Date dateTime) {this.dateTime = dateTime;}

    public String getOrderStatus() {return orderStatus;}
    public void setOrderStatus(String orderStatus) {this.orderStatus = orderStatus;}

    public Set<PizzaOrder> getPizzaOrderSet() {return pizzaOrderSet;}
    public void setPizzaOrderSet(Set<PizzaOrder> pizzaOrderSet) {this.pizzaOrderSet = pizzaOrderSet;}

    public Long[] getPizzaOrderID() {return pizzaOrderID;}
    public void setPizzaOrderID(Long[] pizzaOrderID) {this.pizzaOrderID = pizzaOrderID;}

    public double getOrderCost() {return orderCost;}
    public void setOrderCost(double orderCost) {this.orderCost = orderCost;}
}
