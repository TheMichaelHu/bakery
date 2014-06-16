import java.text.DecimalFormat;
import java.util.HashMap;

public class Database {
    private HashMap<String, Customer> customers;
    private HashMap<String, Item> inventory;

    Database() {
        this.customers = new HashMap<String, Customer>();
        this.inventory = new HashMap<String, Item>();
    }

    Database(String custFile, String invFile) {
        // TODO need to integrate customer and inventory files into database
    }

    void addCustomer(String name, String adrs, String phone, int cdt,
            boolean isLoyal) {
        this.customers.put(name, new Customer(name, adrs, phone, cdt, isLoyal));
    }

    void removeCustomer(String name) {
        this.customers.remove(name);
    }

    void addItem(String name, double price) {
        this.inventory.put(name, new Item(name, price));
    }

    void removeItem(String name) {
        this.inventory.remove(name);
    }

    int totalPurchases() {
        int total = 0;
        for (Customer c : this.customers.values()) {
            total += c.totalPurchases();
        }
        return total;
    }

    public String toString() {
        String str = "Name/tPrice";
        for (Item i : this.inventory.values()) {
            DecimalFormat df = new DecimalFormat("#.00");
            str += "\n" + i.name + "/t$" + df.format(i.price);
        }
        return str;
    }
}
