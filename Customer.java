/**
 * Class that represents a customer
 * 
 * @author Michael Hu and William Enright
 * @version 6/15/2014
 */
public class Customer {
    /** The id of the customer */
    int id;
    /** The name of the customer */
    String name;
    /** The customer's address */
    String address;
    String city;
    String state;
    String zipcode;
    /** True if the customer has a loyalty card */
    int points;

    /**
     * Constructs a new customer
     * Requires that none of the inputs are null
     * 
     * @param i
     *            the id of the customer
     * 
     * @param n
     *            the name of the customer
     * 
     * @param a
     *            the customer's address
     * 
     * @param c
     *            the customer's city
     * @param s
     *            the customer's state
     * 
     * @param z
     *            the customer's zipcode
     * 
     * @param p
     *            true customer's loyalty points
     */
    Customer(int i, String n, String a, String c, String s, String z, int p) {
        this.id = i;
        this.name = n;
        this.address = a;
        this.city = c;
        this.state = s;
        this.zipcode = z;
        this.points = p;
    }
}
