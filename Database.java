import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
                            arr[3], arr[4], arr[5]);
                    this.customers.put(c.name, c);
                }
                else {
                    c = this.customers.get(arr[1]);
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
                this.orders.put(o.id, o);
            }
        }
        else {
            throw new RuntimeException("Incorrect database file format.");
        }

        // Update customer info for all orders
        for (Order o : this.orders.values()) {
            o.customer.update(o);
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

    void addOrder(String cust, HashMap<Integer, Integer> list) { // Item id,
                                                                 // quantity
        if (this.customers.get(cust) == null) {
            System.err.println("Please add the new customer to "
                    + "the database first");
            return;
        }

        // Calculate next id
        int id = 0;
        for (Order o : this.orders.values()) {
            if (o.id > id) {
                id = o.id + 1;
            }
        }
        // Get date
        String today = Calendar.MONTH + "/" + Calendar.DATE + "/"
                + Calendar.YEAR;

        // Generate item hashmap
        HashMap<Item, Integer> map = new HashMap<Item, Integer>();
        for (Integer i : list.keySet()) {
            Item it = this.inventory.get(i);
            int quantity = list.get(i);
            map.put(it, quantity);
        }

        Order o = new Order(this.customers.get(cust), id, today, map);
        this.orders.put(o.id, o);
    }

    void pickupOrder(int id, int discount) {
        Order o = this.orders.get(id);
        o.paid = true;
        o.pickupDate = Calendar.MONTH + "/" + Calendar.DATE + "/"
                + Calendar.YEAR;
        o.discountUsed = discount;
        o.customer.update(o);
    }

    void removeOrder(int id) {
        Order o = this.orders.get(id);
        o.customer.undoUpdate(o);
        this.orders.remove(id);
    }

    boolean hasOrder(int id) {
        return this.orders.containsKey(id);
    }

    void addCustomer(String name, String adr, String city, String state,
            String zip) {
        Customer c = new Customer(this.customers.size(), name, adr, city,
                state, zip);
        this.customers.put(c.name, c);
    }

    void updateCustomer(String oldName, String newName, String adr,
            String city, String state, String zip) {
        Customer c = this.customers.get(oldName);
        c.name = newName;
        c.address = adr;
        c.city = city;
        c.state = state;
        c.zipcode = zip;
    }

    boolean hasCustomer(String name) {
        return this.customers.containsKey(name);
    }

    boolean hasCustomer(int id) {
        for (Customer c : this.customers.values()) {
            if (c.id == id) {
                return false;
            }
        }
        return true;
    }

    Customer getCustomer(String name) {
        return this.customers.get(name);
    }

    void addItem(String name, String category, double price) {
        // Calculate next id
        int id = 0;
        for (Item i : this.inventory.values()) {
            if (i.id > id) {
                id = i.id + 1;
            }
        }
        Item i = new Item(id, name, category, price);
        this.inventory.put(i.id, i);
    }

    void removeItem(int id) {
        this.inventory.get(id).category = "Discontinued";
    }

    boolean hasItem(int id) {
        return this.inventory.containsKey(id);
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
                            + i.price + "\t" + o.totalPrice() + "\t"
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

    /** Prints all orders */
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
                        + i.category + "\t" + quantity + "\t" + i.price + "\t"
                        + o.totalPrice() + "\t" + o.discountUsed + "\t"
                        + o.finalPrice() + "\t" + c.discountPoints + "\t"
                        + c.loyalPoints;
            }
        }
        System.out.println(data);
    }
    
    /**
     * Prints all the orders placed by the given customer
     * @param customer The given customer's name
     */
    void printCustomerOrders(String customer) {
        String data = "CustomerID\tName\t"
                + "OrderID\tPaid?\tOrderDate\tPickupDate\t"
                + "BakeryItemID\tBakeryItemName\tBakeryItemCategory\t"
                + "Quantity\tPrice\tTotal\tDiscountUsedOnOrder\tTotalDue";
        for (Order o : this.orders.values()) {
            Customer c = o.customer;
            if (c.name.equals(customer)) {
                for (Item i : o.items.keySet()) {
                    int quantity = o.items.get(i);
                    data += "\n" + c.id + "\t" + c.name + "\t"
                            + o.id + "\t" + o.paid + "\t" + o.orderDate + "\t"
                            + o.pickupDate + "\t" + i.id + "\t" + i.name + "\t"
                            + i.category + "\t" + quantity + "\t" 
                            + i.price + "\t" + o.totalPrice() + "\t" 
                            + o.discountUsed + "\t" + o.finalPrice();
                }
            }
        }
        System.out.println(data);
        System.out.println();
    }
    
    /**
     * Prints all orders placed on a certain date
     * @param date The given date
     */
    void printPlacedDateOrders(String date) {
        String data = "CustomerID\tName\t"
                + "OrderID\tPaid?\tOrderDate\tPickupDate\t"
                + "BakeryItemID\tBakeryItemName\tBakeryItemCategory\t"
                + "Quantity\tPrice\tTotal\tDiscountUsedOnOrder\tTotalDue";
        for (Order o : this.orders.values()) {
            Customer c = o.customer;
            if (o.orderDate.equals(date)) {
                for (Item i : o.items.keySet()) {
                    int quantity = o.items.get(i);
                    data += "\n" + c.id + "\t" + c.name + "\t"
                            + o.id + "\t" + o.paid + "\t" + o.orderDate + "\t"
                            + o.pickupDate + "\t" + i.id + "\t" + i.name + "\t"
                            + i.category + "\t" + quantity + "\t" 
                            + i.price + "\t" + o.totalPrice() + "\t" 
                            + o.discountUsed + "\t" + o.finalPrice();
                }
            }
        }
        System.out.println(data);
        System.out.println();
    }
    
    /**
     * Prints all orders finished/picked up on a certain date
     * @param date The given date
     */
    void printFinishedDateOrders(String date) {
        String data = "CustomerID\tName\t"
                + "OrderID\tPaid?\tOrderDate\tPickupDate\t"
                + "BakeryItemID\tBakeryItemName\tBakeryItemCategory\t"
                + "Quantity\tPrice\tTotal\tDiscountUsedOnOrder\tTotalDue";
        for (Order o : this.orders.values()) {
            Customer c = o.customer;
            if (o.pickupDate.equals(date)) {
                for (Item i : o.items.keySet()) {
                    int quantity = o.items.get(i);
                    data += "\n" + c.id + "\t" + c.name + "\t"
                            + o.id + "\t" + o.paid + "\t" + o.orderDate + "\t"
                            + o.pickupDate + "\t" + i.id + "\t" + i.name + "\t"
                            + i.category + "\t" + quantity + "\t" 
                            + i.price + "\t" + o.totalPrice() + "\t" 
                            + o.discountUsed + "\t" + o.finalPrice();
                }
            }
        }
        System.out.println(data);
        System.out.println();
    }
    
    /**
     * Prints all orders of the given item id
     * @param itemId The id of the item
     */
    void printItemOrders(int itemId) {
        String data = "CustomerID\tName\t"
                + "OrderID\tPaid?\tOrderDate\tPickupDate\t"
                + "BakeryItemID\tBakeryItemName\tBakeryItemCategory\t"
                + "Quantity\tPrice\tTotal\tDiscountUsedOnOrder\tTotalDue";
        for (Order o : this.orders.values()) {
            Customer c = o.customer;
            for (Item i : o.items.keySet()) {
                if (i.id == itemId) {
                    int quantity = o.items.get(i);
                    data += "\n" + c.id + "\t" + c.name + "\t"
                            + o.id + "\t" + o.paid + "\t" + o.orderDate + "\t"
                            + o.pickupDate + "\t" + i.id + "\t" + i.name + "\t"
                            + i.category + "\t" + quantity + "\t" 
                            + i.price + "\t" + o.totalPrice() + "\t" 
                            + o.discountUsed + "\t" + o.finalPrice();
                }
            }
        }
        System.out.println(data);
        System.out.println();
    }
    
    void printUnpaidOrders() {
        String data = "CustomerID\tName\t"
                + "OrderID\tPaid?\tOrderDate\tPickupDate\t"
                + "BakeryItemID\tBakeryItemName\tBakeryItemCategory\t"
                + "Quantity\tPrice\tTotal\tDiscountUsedOnOrder\tTotalDue";
        for (Order o : this.orders.values()) {
            Customer c = o.customer;
            if (!o.paid) {
                for (Item i : o.items.keySet()) {
                    int quantity = o.items.get(i);
                    data += "\n" + c.id + "\t" + c.name + "\t"
                            + o.id + "\t" + o.paid + "\t" + o.orderDate + "\t"
                            + o.pickupDate + "\t" + i.id + "\t" + i.name + "\t"
                            + i.category + "\t" + quantity + "\t" 
                            + i.price + "\t" + o.totalPrice() + "\t" 
                            + o.discountUsed + "\t" + o.finalPrice();
                }
            }
        }
        System.out.println(data);
        System.out.println();
    }

    void printInventory() {
        String data = "BakeryItemID\tBakeryItemName\tCategory\tPrice";
        for (Item i : this.inventory.values()) {
            if (i.category.equals("Discontinued")) {
                continue;
            }
            data += "\n" + i.id + "\t" + i.name + "\t" + i.category + "\t"
                    + i.price;
        }
        System.out.println(data);
    }
}
