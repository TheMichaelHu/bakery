import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class that represents a database of orders, customers, and items in the
 * inventory
 * 
 * @author Michael Hu and William Enright
 * @version 6/18/2014
 */
public class Database {
    /** all the orders received by the bakery */
    private HashMap<Integer, Order> orders;
    /** all the customers of the bakery */
    private HashMap<String, Customer> customers;
    /** all the items available in the bakery */
    private HashMap<Integer, Item> inventory;
    /** the file to save the orders to when the program exits */
    private File orderFile;
    /** the file to save the inventory to when the program exits */
    private File inventoryFile;

    Database() {
        this.orders = new HashMap<Integer, Order>();
        this.customers = new HashMap<String, Customer>();
        this.inventory = new HashMap<Integer, Item>();
        this.orderFile = new File("ord.txt");
        this.inventoryFile = new File("inv.txt");
    }

    Database(String ordFileName, String invFileName) {
        this();
        this.orderFile = new File(ordFileName + ".txt");
        this.inventoryFile = new File(invFileName + ".txt");
    }

    Database(String ordFile, String invFile, String newOrdFile,
            String newInvFile) {
        this(newOrdFile, newInvFile);
        this.populateInventory(this.parseFile(invFile));
        this.populateOrders(this.parseFile(ordFile));
    }

    private ArrayList<String[]> parseFile(String file) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        try {
            Scanner scan = new Scanner(new File(file + ".txt"));
            while (scan.hasNextLine()) {
                list.add(scan.nextLine().split("\t"));
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.err.println(file + " does not exist");
            throw new RuntimeException();
        }
        return list;
    }

    private void populateOrders(ArrayList<String[]> list) {
        if (!list.isEmpty() && list.get(0).length == 20
                && list.get(0)[0].equals("CustomerID")
                && list.get(0)[1].equals("LastName")
                && list.get(0)[2].equals("Address")
                && list.get(0)[3].equals("City")
                && list.get(0)[4].equals("State")
                && list.get(0)[5].equals("ZipCode")
                && list.get(0)[6].equals("OrderID")
                && list.get(0)[7].equals("Paid?")
                && list.get(0)[8].equals("OrderDate")
                && list.get(0)[9].equals("PickupDate")
                && list.get(0)[10].equals("BakeryItemID")
                && list.get(0)[11].equals("BakeryItemName")
                && list.get(0)[12].equals("BakeryItemCategory")
                && list.get(0)[13].equals("Quantity")
                && list.get(0)[14].equals("Price")
                && list.get(0)[15].equals("Total")
                && list.get(0)[16].equals("DiscountUsedOnOrder")
                && list.get(0)[17].equals("TotalDue")
                && list.get(0)[18].equals("AvailableDiscount")
                && list.get(0)[19].equals("CurrentLoyalty"))
        {
            list.remove(0);
            for (String[] arr : list) {
                Customer c;
                if (!this.customers.containsKey(arr[1])) {
                    c = new Customer(Integer.parseInt(arr[0]), arr[1], arr[2],
                            arr[3], arr[4], arr[5],
                            Double.parseDouble(arr[19]),
                            Double.parseDouble(arr[18]));
                    this.addCustomer(c);
                }
                else {
                    c = this.customers.get(arr[1]).update(arr);
                }
                Item i = this.inventory.get(Integer.parseInt(arr[10]));
                if (i == null
                        || !(arr[11].equals(i.name)
                                && arr[12].equals(i.category) && Double
                                .parseDouble(arr[14]) == i.price))
                {
                    throw new RuntimeException("Incorrect product information.");
                }
                Order o;
                if (!this.orders.containsKey(Integer.parseInt(arr[6]))) {
                    HashMap<Item, Integer> map = new HashMap<Item, Integer>();
                    map.put(i, Integer.parseInt(arr[13]));
                    o = new Order(c, Integer.parseInt(arr[6]),
                            arr[7].equals("Yes"), arr[8], arr[9], map,
                            Double.parseDouble(arr[16]));
                }
                else {
                    o = this.orders.get(Integer.parseInt(arr[6]));
                    o.addItem(i, Integer.parseInt(arr[13]));
                }
                this.addOrder(o);
            }
        }
        else {
            throw new RuntimeException("Incorrect database file format.");
        }
    }

    private void populateInventory(ArrayList<String[]> list) {
        if (!list.isEmpty() && list.get(0).length == 4
                && list.get(0)[0].equals("BakeryItemID")
                && list.get(0)[1].equals("BakeryItemName")
                && list.get(0)[2].equals("Category")
                && list.get(0)[3].equals("Price"))
        {
            list.remove(0);
            for (String[] arr : list) {
                Item i = new Item(Integer.parseInt(arr[0]), arr[1], arr[2],
                        Double.parseDouble(arr[3]));
                this.inventory.put(i.id, i);
            }
        }
        else {
            System.err.println("Incorrect inventory file format.");
            throw new RuntimeException();
        }
    }
    
    HashMap<Item, Integer> getItems(int id) {
        return this.orders.get(id).items;
    }

    void addOrder(Order o) {
        this.orders.put(o.id, o);
    }
    
    void removeOrder(int id) {
        this.orders.remove(id);
    }
    
    boolean hasOrder(int id) {
        return this.orders.containsKey(id);
    }

    void addCustomer(Customer c) {
        this.customers.put(c.name, c);
    }

    void removeCustomer(String name) {
        this.customers.remove(name);
    }

    void addItem(Item i) {
        this.inventory.put(i.id, i);
    }

    void removeItem(int id) {
        this.inventory.remove(id);
    }
    
    boolean hasItem(int id) {
        return this.inventory.containsKey(id);
    }

    boolean hasCustomer(String name) {
        return this.customers.containsKey(name);
    }
    
    boolean hasCustomer(int id) {
        for(Customer c : this.customers.values()) {
            if(c.id == id) {
                return false;
            }
        }
        return true;
    }
    
    Customer getCustomer(String name) {
        return this.customers.get(name);
    }

    int totalPurchases() {
        return this.orders.size();
    }

    void writeToFiles() {
        try {
            BufferedWriter writer;

            // Writing to inventory file
            writer = new BufferedWriter(new FileWriter(this.inventoryFile));
            String data = "BakeryItemID\tBakeryItemName\tCategory\tPrice";
            for (Item i : this.inventory.values()) {
                data += "\n" + i.id + "\t" + i.name + "\t" + i.category + "\t"
                        + i.price;
            }
            writer.write(data);
            writer.close();

            // Writing to order file
            writer = new BufferedWriter(new FileWriter(this.orderFile));
            data = "CustomerID\tLastName\tAddress\tCity\tState\t"
                    + "ZipCode\tOrderID\tPaid?\tOrderDate\tPickupDate\t"
                    + "BakeryItemID\tBakeryItemName\tBakeryItemCategory\t"
                    + "Quantity\tPrice\tTotal\tDiscountUsedOnOrder\tTotalDue"
                    + "\tAvailableDiscount\tCurrentLoyalty";
            for (Order o : this.orders.values()) {
                Customer c = o.customer;
                for (Item i : o.items.keySet()) {
                    int quantity = o.items.get(i);
                    data += "\n" + c.id + "\t" + c.name + "\t" + c.address
                            + "\t" + c.city + "\t" + c.state + "\t" + c.zipcode
                            + "\t" + o.id + "\t" + o.paid + "\t" + o.orderDate
                            + "\t" + o.pickupDate + "\t" + i.id + "\t" + i.name
                            + "\t" + i.category + "\t" + quantity + "\t"
                            + i.price * quantity + "\t" + o.totalPrice() + "\t"
                            + o.discountUsed + "\t" + o.finalPrice() + "\t"
                            + c.discountPoints + "\t" + c.loyalPoints;
                }
            }
            writer.write(data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printOrders() {
        String data = "CustomerID\tLastName\tAddress\tCity\tState\t"
                + "ZipCode\tOrderID\tPaid?\tOrderDate\tPickupDate\t"
                + "BakeryItemID\tBakeryItemName\tBakeryItemCategory\t"
                + "Quantity\tPrice\tTotal\tDiscountUsedOnOrder\tTotalDue"
                + "\tAvailableDiscount\tCurrentLoyalty";
        for (Order o : this.orders.values()) {
            Customer c = o.customer;
            for (Item i : o.items.keySet()) {
                int quantity = o.items.get(i);
                data += "\n" + c.id + "\t" + c.name + "\t" + c.address + "\t"
                        + c.city + "\t" + c.state + "\t" + c.zipcode + "\t"
                        + o.id + "\t" + o.paid + "\t" + o.orderDate + "\t"
                        + o.pickupDate + "\t" + i.id + "\t" + i.name + "\t"
                        + i.category + "\t" + quantity + "\t" + i.price
                        * quantity + "\t" + o.totalPrice() + "\t"
                        + o.discountUsed + "\t" + o.finalPrice() + "\t"
                        + c.discountPoints + "\t" + c.loyalPoints;
            }
        }
        System.out.println(data);
    }
    
    void printInventory() {
        String data = "BakeryItemID\tBakeryItemName\tCategory\tPrice";
        for (Item i : this.inventory.values()) {
            data += "\n" + i.id + "\t" + i.name + "\t" + i.category + "\t"
                    + i.price;
        }
        System.out.println(data);
    }
}
