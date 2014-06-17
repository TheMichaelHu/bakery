import java.util.Calendar;

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
    Item item;
    int quantity;
    int discountUsed;
    
    
    /**
     * Constructs a new order
     * Requires that list is not null
     * 
     * @param list
     *            a list of items
     */
    Order(Customer c, int i, boolean p, Item it, int q, int d) {
        this.customer = c;
        this.id = i;
        this.paid = p;
        this.orderDate = Calendar.MONTH + "/" + Calendar.DAY_OF_MONTH + "/"
                + Calendar.YEAR;
        this.item = it;
        this.quantity = q;
        this.discountUsed = d;
    }

    /**
     * returns the number of items in the order
     * 
     * @return the number of items in the order
     */
    int numItems() {
        return this.quantity;
    }

    /**
     * returns the total cost of the order
     * 
     * @return the total cost of the order
     */
    double totalCost() {
        return this.item.price*this.quantity - this.discountUsed;
    }
}
