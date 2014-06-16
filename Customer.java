import java.util.HashMap;

/**
 * Class that represents a customer
 * 
 * @author Michael Hu and William Enright
 * @version 6/15/2014
 */
public class Customer {
    /** The name of the customer */ 
    String name;
    /** The customer's address */ 
    String address;
    /** The customer's phone number */ 
    String phone;
    /** The customers credit card number */ 
    int creditCard;
    /** A list of the customer's past orders */ 
    HashMap<String, Order> orders;
    /** The customer's loyalty points */ 
    int points;
    /** True if the customer has a loyalty card */ 
    boolean isLoyal;
    
    /**
     * Constructs a new customer
     * Requires that none of the inputs are null
     * 
     * @param n
     *            the name of the customer
     * 
     * @param a
     *            the customer's address
     * 
     * @param p
     *            the customer's phone number
     * @param c
     *            the customer's credit card number
     * @param l
     *            true if the customer has a loyalty card
     */
    Customer(String n, String a, String p, int c, boolean l) {
        this.name = n;
        this.address = a;
        this.phone = p;
        this.creditCard = c;
        this.orders = new HashMap<String, Order>();
        this.points = 0;
        this.isLoyal = l;
    }
}
