import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Database {
    private HashMap<Integer, Order> orders;
    private HashMap<String, Customer> customers;
    private HashMap<String, Item> inventory;
    private File orderFile;
    private File inventoryFile;

    Database() {
        this.orders = new HashMap<Integer, Order>();
        this.customers = new HashMap<String, Customer>();
        this.inventory = new HashMap<String, Item>();
        this.orderFile = new File("ord");
        this.inventoryFile = new File("inv");
    }
    
    Database(String ordFileName, String invFileName) {
        this();
        this.orderFile = new File(ordFileName);
        this.inventoryFile = new File(invFileName);
    }

    Database(String ordFile, String invFile, String newOrdFile,
            String newInvFile) {
        this(newOrdFile, newInvFile);
        this.populateOrders(this.parseFile(ordFile));
        this.populateInventory(this.parseFile(invFile));
    }

    private ArrayList<String[]> parseFile(String file) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        try {
            Scanner scan = new Scanner(new File(file));
            while (scan.hasNextLine()) {
                list.add(scan.nextLine().split("\t"));
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.err.println(file + "does not exist");
        }
        return list;
    }

    private void populateOrders(ArrayList<String[]> list) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(this.orderFile));
            for (String[] arr : list) {
                for (String str : arr) {
                    writer.write(str + "\t");
                }
                writer.write("\n");
            }
            writer.close();
        }
        catch (IOException e1) {
            System.err.println("Couldn't write to order file.");
        }

        try {
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
                    if(!this.customers.containsKey(arr[0])) {
                        c = new Customer(Integer.parseInt(arr[0]), arr[1],
                                arr[2], arr[3], arr[4], arr[5],
                                Integer.parseInt(arr[19]), Integer.parseInt(arr[18]));
                        this.addCustomer(c);
                    }
                    else {
                        c = this.customers.get(arr[0]).update(list);
                    }
                    Item i = this.inventory.get(Integer.parseInt(arr[10]));
                    if (!(arr[11].equals(i.name) && arr[12].equals(i.category) && Integer
                            .parseInt(arr[14]) == i.price))
                    {
                        System.err.println("Incorrect product information.");
                    }
                    Order o = new Order(c, Integer.parseInt(arr[6]),
                            arr[7].equals("Yes"), i, Integer.parseInt(arr[13]),
                            Integer.parseInt(arr[16]));
                    this.addOrder(o);
                }
            }
            else {
                System.err.println("Incorrect database file format.");
            }
        }
        catch (Exception e) {
            System.err.println("Incorrect database file format.");
        }
    }

    private void populateInventory(ArrayList<String[]> list) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(this.inventoryFile));
            for (String[] arr : list) {
                for (String str : arr) {
                    writer.write(str + "\t");
                }
                writer.write("\n");
            }
            writer.close();
        }
        catch (IOException e1) {
            System.err.println("Couldn't write to inventory file.");
        }

        try {
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
                    this.inventory.put(i.name, i);
                }
            }
            else {
                System.err.println("Incorrect inventory file format.");
            }
        }
        catch (Exception e) {
            System.err.println("Incorrect inventory file format.");
        }
    }

    void addOrder(Order o) {
        this.orders.put(o.id, o);
    }

    void addCustomer(Customer c) {
        this.customers.put(c.name, c);
    }
    
    void removeCustomer(String name) {
        this.customers.remove(name);
    }

    void addItem(Item i) {
        this.inventory.put(i.name, i);
    }

    void removeItem(String name) {
        this.inventory.remove(name);
    }
    
    boolean lookupCustomer(String name) {
        return this.customers.containsKey(name);
    }

    int totalPurchases() {
        return this.orders.size();
    }
}
