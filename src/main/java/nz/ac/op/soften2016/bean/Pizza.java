package nz.ac.op.soften2016.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by adonm on 18/07/2016.
 */
public class Pizza {

    private Long id;
    private String name;
    private String size;
    private int quantity;
    private boolean spicy;
    private double price;
    private Set<PizzaTopping> pizzaToppings = new HashSet<PizzaTopping>();
    private Long[] pizzaToppingsId;

    //Setters & getters

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {	this.quantity = quantity;}

    public String getsize() { return size; }
    public void setSize(String size) { this.size = size; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getSpicy() { return spicy; }
    public void setSpicy(Boolean spicy) { this.spicy = spicy; }

    public Set<PizzaTopping> getPizzaToppings() { return pizzaToppings; }
    public void setPizzaToppings(Set<PizzaTopping> pizzaToppings) { this.pizzaToppings = pizzaToppings; }

    public Long[] getPizzaToppingsId() { return pizzaToppingsId; }
    public void setPizzaToppingsId(Long[] pizzaToppingsId) { this.pizzaToppingsId = pizzaToppingsId; }

    //Methods
    public Pizza(){    }

    public Pizza(String name){ this.name = name; }
    public Pizza(String name, String size){ this.name = name; this.size = size;}
    public Pizza(String name, String size, boolean spicy){ this.name = name;this.size = size; this.setSpicy(spicy); }
    public Pizza(String name, String size, boolean spicy, double price){ this.name = name;this.size = size; this.setSpicy(spicy);this.price = price;}
    public Pizza(String name, String size, boolean spicy, int quantity){ this.name = name;this.size = size; this.setSpicy(spicy);this.quantity = quantity;}

    public void addPizzaTopping(PizzaTopping pizzaTopping){
        pizzaTopping.setPizza(this);
        pizzaToppings.add(pizzaTopping);
    }

    public void createPizzaTopping(Topping topping, int numToppings){
        PizzaTopping pizzaTopping = new PizzaTopping();
        pizzaTopping.setTopping(topping);
        pizzaTopping.setToppingsPerPizza(numToppings);
        addPizzaTopping(pizzaTopping);
    }
    public boolean isSpicy() {
        return spicy;
    }
    public void setSpicy(boolean spicy) {
        this.spicy = spicy;
    }

}


/*
package nz.ac.op.soften2016.bean;

import java.util.HashSet;
import java.util.Set;

*/
/**
 * Created by adonm on 18/07/2016.
 *//*

public class Pizza {

    private Long id;
    private int barcode;
    private int quantity;
    private double cost;
    private String name;
    private Set<PizzaTopping> pizzaToppings = new HashSet<PizzaTopping>();
    private Long[] pizzaToppingsId;

    //Methods
    public Pizza(){    }

    public Pizza(String name){ this.name = name; }

    public void addPizzaTopping(PizzaTopping pizzaTopping){
        pizzaTopping.setPizza(this);
        pizzaToppings.add(pizzaTopping);
    }

    public void createPizzaTopping(Topping topping){
        PizzaTopping pizzaTopping = new PizzaTopping();
        pizzaTopping.setTopping(topping);
        addPizzaTopping(pizzaTopping);
    }
    //Setters & getters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<PizzaTopping> getPizzaToppings() { return pizzaToppings; }
    public void setPizzaToppings(Set<PizzaTopping> pizzaToppings) { this.pizzaToppings = pizzaToppings; }

    public Long[] getPizzaToppingsId() { return pizzaToppingsId; }
    public void setPizzaToppingsId(Long[] pizzaToppingsId) { this.pizzaToppingsId = pizzaToppingsId; }

    public int getBarcode() { return barcode;}
    public void setBarcode(int barcode) {this.barcode = barcode;}

    public int getQuantity() {return quantity ;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public double getCost() {return cost;}
    public void setCost(double cost) {this.cost = cost;}
}*/
