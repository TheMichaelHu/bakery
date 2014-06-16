/**
 * Class that represents an item available in the bakery
 * 
 * @author Michael Hu and William Enright
 * @version 6/15/2014
 */
public class Item {
    /** the name of the item */
    String name;
    /** the irtem's price */
    double price;

    /**
     * Constructs a new item
     * Requires that none of the inputs are null and p > 0
     * 
     * @param n
     *            the name of the item
     * 
     * @param p
     *            the item's price
     */
    Item(String n, double p) {
        this.name = n;
        this.price = p;
    }
}
