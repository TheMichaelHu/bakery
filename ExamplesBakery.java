import java.util.HashMap;

import tester.*;

/**
 * Tester for Bakery
 * 
 * @author Michael Hu and William Enright
 * @version 6/18/2014
 */
public class ExamplesBakery {
    /** example database */
    Database d;

    /**
     * Initializes example KVMaps
     */
    public ExamplesBakery() {
        d = new Database("orders", "bakeryItems", "ord", "inv");
    }

    /**
     * resets the database
    */
    private void reset() {
        d = new Database("orders", "bakeryItems", "ord", "inv");
    }

    /**
     * Tests the addOrder method
     * 
     * @param t
     *            a Tester
     */
    public void testAddOrder(Tester t) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(72, 5);
        t.checkExpect(d.totalPurchases(), 20);
        d.addOrder("Williams", map);
        t.checkExpect(d.totalPurchases(), 20);
        reset();
    }
}