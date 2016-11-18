package nz.ac.op.soften2016.bean;


public class PizzaOrder {

    private Long id;
    private Long pizzaId;
    private Long orderId;
    private int quantity;
    private Pizza pizza;
    private XOrder xorder;

    // Constructor
    public PizzaOrder()
    {

    }

    //Setters & getters

    public XOrder getOrder() {
        return xorder;
    }
    public void setOrder(XOrder xorder) {
        this.xorder = xorder;
    }

    public Pizza getPizza() {
        return pizza;
    }
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Long getPizzaId() {
        return pizzaId;
    }
    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

/**
 * Created by thoml on 24/08/2016.
 */
}
/*package nz.ac.op.soften2016.bean; 
 

public class PizzaOrder {
    private Long id;
    private Pizza pizza;
    private Order order;

    private Long orderID;
    private Long pizzaID;
    private int quantity;


    public PizzaOrder(){};

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Long getOrderID() {return orderID;}
    public void setOrderID(Long orderID) {this.orderID = orderID;}

    public Long getPizzaID() {return pizzaID;}
    public void setPizzaID(Long pizzaID) { this.pizzaID = pizzaID;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public Pizza getPizza() {return pizza;}
    public void setPizza(Pizza pizza) {this.pizza = pizza;}

    public Order getOrder() {return order;}
    public void setOrder(Order order) {this.order = order;}
}*/
