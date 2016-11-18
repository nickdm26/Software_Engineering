package nz.ac.op.soften2016.bean;

//=============================================================================================================//
//Matt 26/10/16

public class PizzaTopping {

    private Long id;
    private Pizza pizza;
    private Topping topping;
    private int toppingsPerPizza;

    private Long pizzaId;
    private Long toppingId;

    public PizzaTopping(){ }

    public PizzaTopping(Pizza pizza, Topping topping){
        this.pizza = pizza;
        this.topping = topping;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public int getToppingsPerPizza() { return toppingsPerPizza; }
    public void setToppingsPerPizza(int quantity) { toppingsPerPizza = quantity; }

    public Pizza getPizza() { return pizza; }
    public Topping getTopping() { return topping; }

    public void setPizza(Pizza pizza) { this.pizza = pizza; }
    public void setTopping(Topping topping) { this.topping = topping; }

    public Long getPizzaId() { return pizzaId; }
    public Long getToppingId() { return toppingId; }

    public void setPizzaId(Long pizzaId) { this.pizzaId = pizzaId; }
    public void setToppingId(Long toppingId) { this.toppingId = toppingId; }
}
//=============================================================================================================//
