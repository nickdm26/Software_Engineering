package nz.ac.op.soften2016.bean;
import java.util.HashSet;
import java.util.Set;
public class XOrder {

    private Long id;
    private XUser user;
    private String address;
    private double orderCost;
    private String orderDate;
    private String orderStatus;

    private String orderType;
    private boolean orderComplete;
    private boolean scannerOrder;

    private Set<PizzaOrder> thePizzaOrders = new HashSet<PizzaOrder>();

    // Constructor
    public XOrder()
    {
        this.orderComplete = false;
        this.orderCost = 0.0;
        this.scannerOrder = false;
    }
    public XOrder(boolean orderComplete)
    {
        this.orderComplete = orderComplete;
        this.orderCost = 0.0;
        this.scannerOrder = false;
    }

    public XOrder(boolean orderComplete, boolean scannerOrder)
    {
        this.orderComplete = orderComplete;
        this.orderCost = 0.0;
        this.scannerOrder = scannerOrder;
    }

    public XOrder(boolean orderComplete, double orderCost)
    {
        this.orderComplete = orderComplete;
        this.orderCost = orderCost;
        this.scannerOrder = false;
    }

    public void createNewPizzaOrder(Pizza pizza, int quantity)
    {
        PizzaOrder p1 = new PizzaOrder();
        p1.setPizza(pizza);
        p1.setOrder(this);
        p1.setQuantity(quantity);
        thePizzaOrders.add(p1);
    }

    public void deletePizzaFromOrder(PizzaOrder pizzaOrder)
    {
        thePizzaOrders.remove(pizzaOrder);
    }

    public String getOrderType() {  return orderType;}

    public void setOrderType(String orderType) {    this.orderType = orderType;}
    public double getOrderCost() {  return orderCost;}

    public void setOrderCost(double totalCost) {    this.orderCost = totalCost; }

    public String getOrderDate() {  return orderDate;   }

    public void setOrderDate(String orderDate) {    this.orderDate = orderDate;}

    public String getOrderStatus() {    return orderStatus;}

    public void setOrderStatus(String status) { this.orderStatus = status;}

    public Long getId() {   return id;}

    public void setId(Long id) {    this.id = id;}

    public Set<PizzaOrder> getThePizzaOrders() {  return thePizzaOrders;}

    public void setThePizzaOrders(Set<PizzaOrder> thePizzaOrders) {   this.thePizzaOrders = thePizzaOrders;}

    public String getAddress() {    return address;}

    public void setAddress(String address) {    this.address = address;}

    public XUser getUser() {    return user;}

    public void setUser(XUser user) {   this.user = user;}

    public boolean getOrderComplete() { return orderComplete;}

    public void setOrderComplete(boolean isComplete) {  this.orderComplete = isComplete;}

    public boolean isScannerOrder() {   return scannerOrder;}
    public void setScannerOrder(boolean scannerOrder) { this.scannerOrder = scannerOrder;}
} 
