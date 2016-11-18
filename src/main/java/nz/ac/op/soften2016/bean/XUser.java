package nz.ac.op.soften2016.bean;
import java.util.HashSet;
import java.util.Set;

public class XUser {
	
    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private double ticks;   //reward card ticks
    private double pizzasCredit; //number of pizzas to credit
    
    private Set<XOrder> theOrders = new HashSet<XOrder>();
    
    //Setters & getters  
    
    public XUser()
    {   
        
    }
    
    public void addXOrder(XOrder xorder){
        //setTheOrders(theOrders);(this); 
    }
    
    public XUser(String lastName, String firstName) {this.setLastName(lastName);this.firstName = firstName;}
    public XUser(String lastName, String firstName, String email)
    {this.setLastName(lastName);this.firstName = firstName;this.setEmail(email);}
    
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
 
    public Long getId() {return id; }
    public void setId(Long id) {this.id = id;}
    
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName; }
    
    public Set<XOrder> getTheOrders() {   return theOrders;}
    public void setTheOrders(Set<XOrder> theOrders) {this.theOrders = theOrders;}
    
    //get and set ticks for rewards
    public double getTicks()    {return ticks;}
    public void setTicks(double ticks)  {this.ticks = ticks;}
    //get and set free
    public double getPizzasCredit() {return pizzasCredit;}
    public void setPizzasCredit(double pizzasCredit)    {this.pizzasCredit = pizzasCredit;}
    
	//get and set for email
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}   
}
