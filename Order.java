import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class that represents an order at the bakery
 * 
 * @author Michael Hu and William Enright
 * @version 6/15/2014
 */
public class Order {
    /** the items in the order */
    ArrayList<Item> items;
    /** the date the order was placed */
    String date;

    /**
     * Constructs a new order
     * Requires that list is not null
     * 
     * @param list
     *            a list of items
     */
    Order(ArrayList<Item> list) {
        this.items = list;
        this.date = Calendar.MONTH + "/" + Calendar.DAY_OF_MONTH + "/"
                + Calendar.YEAR;
    }

    /**
     * returns the number of items in the order
     * 
     * @return the number of items in the order
     */
    int numItems() {
        return this.items.size();
    }

    /**
     * returns the total cost of the order
     * 
     * @return the total cost of the order
     */
    double totalCost() {
        int total = 0;
        for (Item i : this.items) {
            total += i.price;
        }
        return total;
    }
}
