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
    double loyalPoints;
    /** gives the customer a discount on their next purchase */
    double discountPoints;

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
    Customer(int i, String n, String a, String c, String s, String z, double lp,
            double dp) {
        this.id = i;
        this.name = n;
        this.address = a;
        this.city = c;
        this.state = s;
        this.zipcode = z;
        this.loyalPoints = lp;
        this.discountPoints = dp;
    }

    /**
     * Updates a customer after their latest order
     * Requires that the given array follows the structure of an order
     * 
     * @param arr
     *            String array representation of an order
     * 
     * @return an updated character
     */
    Customer update(String[] arr) {
        this.loyalPoints += Integer.parseInt(arr[13])
                * Double.parseDouble(arr[14]);
        if (this.loyalPoints >= 100) {
            discountPoints += (int) loyalPoints / 100;
            loyalPoints %= 100;
        }
        return this;
    }

    /**
     * Updates the customer after using a discount
     * 
     * @param discount
     *            The amount of money discounted (a negative number)
     */
    void useDiscount(double discount) {
        this.discountPoints += discount;
        this.loyalPoints += discount;
    }
}
