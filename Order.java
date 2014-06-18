import java.util.HashMap;

/**
 * Class that represents an order at the bakery
 * 
 * @author Michael Hu and William Enright
 * @version 6/15/2014
 */
public class Order {
    /** the customer placing the order */
    Customer customer;
    /** the id of the order */
    int id;
    boolean paid;
    /** the date the order was placed */
    String orderDate;
    String pickupDate;
    /** the items in the order */
    HashMap<Item, Integer> items;
    double discountUsed;

    /**
     * Constructs a brand new order
     * Requires that list is not null
     * 
     * @param c
     *            the customer placing the order
     * @param i
     *            the order id
     * @param od
     *            the date the order was placed
     * @param list
     *            a list of the items in the order
     */
    Order(Customer c, int i, String od, HashMap<Item, Integer> list) {
        this.customer = c;
        this.id = i;
        this.paid = false;
        this.orderDate = od;
        this.pickupDate = null;
        this.items = list;
        this.discountUsed = 0;
    }

    /**
     * Constructs a fully completed order
     * Requires that list is not null
     * 
     * @param c
     *            the customer placing the order
     * @param i
     *            the order id
     * @param p
     *            true if the order has been paid
     * @param od
     *            the date the order was placed
     * @param pd
     *            the date the order was picked up
     * @param list
     *            a list of the items in the order
     * @param d
     *            the discount used on the order
     */
    Order(Customer c, int i, boolean p, String od, String pd,
            HashMap<Item, Integer> list, double d) {
        this.customer = c;
        this.id = i;
        this.paid = p;
        this.orderDate = od;
        this.pickupDate = pd;
        this.items = list;
        this.discountUsed = d;
    }

    void addItem(Item i, int num) {
        this.items.put(i, num);
    }

    /**
     * returns the number of items in the order
     * 
     * @return the number of items in the order
     */
    int numItems() {
        int total = 0;
        for (Integer i : this.items.values()) {
            total += i;
        }
        return total;
    }

    /**
     * returns the total price of the order
     * 
     * @return the total price of the order
     */
    double totalPrice() {
        int total = 0;
        for (Item i : this.items.keySet()) {
            total += i.price * this.items.get(i);
        }
        return total;
    }

    /**
     * returns the total cost of the order
     * 
     * @return the total cost of the order
     */
    double finalPrice() {
        return this.totalPrice() + this.discountUsed;
    }
}
