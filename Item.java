/**
 * Class that represents an item available in the bakery
 * 
 * @author Michael Hu and William Enright
 * @version 6/15/2014
 */
public class Item {
    /** the id of the item */
    int id;
    /** the name of the item */
    String name;
    /** the category of the item */
    String category;
    /** the item's price */
    double price;

    /**
     * Constructs a new item
     * Requires that none of the inputs are null and p > 0
     * 
     * @param i
     *            the id of the item
     * 
     * @param n
     *            the name of the item
     * 
     * @param c
     *            the category of the item
     * 
     * @param p
     *            the item's price
     */
    Item(int i, String n, String c, double p) {
        this.id = i;
        this.name = n;
        this.category = c;
        this.price = p;
    }
}
