import java.util.ArrayList;

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
    /** loyalty points, 100 will get the customer 10 discount points */
    int loyalPoints;
    /** gives the customer a discount on their next purchase */
    int discountPoints;

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
     * @param lp
     *            true customer's loyalty points
     * 
     * @param dp
     *            true customer's discount points
     */
    Customer(int i, String n, String a, String c, String s, String z, int lp,
            int dp) {
        this.id = i;
        this.name = n;
        this.address = a;
        this.city = c;
        this.state = s;
        this.zipcode = z;
        this.loyalPoints = lp;
        this.discountPoints = dp;
    }

    Customer update(ArrayList<String[]> list) {
        // TODO update customer with order information
        return this;
    }
}
