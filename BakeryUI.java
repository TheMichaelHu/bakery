import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class to represent the User Interface of the Bakery System
 * @author Will Enright and Michael Hu
 * @version 6/18/2014
 */
public class BakeryUI {
    
    static boolean quit = false;
    static Database data;
    static Scanner input;
    
    /**
     * The main method to start the database  UI
     * @param args Console arguments
     */
    public static void main(String[] args) {
        /* TODO: Add functionality for:
         * Add order (and print receipt)
         * Update bakery item
         * 
         * TODO: Add employee viewing functions
         */
        data = new Database();
        
        input = new Scanner(System.in);
        
        startUp();
        while (!quit) {
            mainMenu();
        }
        
        // TODO: Save to files
    }
    
    /** Displays when starting the system and initializes the database. */
    static void startUp() {
        System.out.println("Welcome to BakeryUI!");
        System.out.println(
                "Enter a command to use the corresponding function:");
        System.out.println("e - Initialize empty database");
        System.out.println("f - Initialize database from files");
        String cmd = input.next();
        if (cmd.equals("e")) {
            // Database is already empty
            System.out.println();
        }
        else if (cmd.equals("f")) {
            System.out.println();
            getOrders();
            System.out.println();
            getInventory();
            System.out.println();
        }
        else {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            startUp();
        }
    }
    
    /** Reads from orders file and initializes the database */
    static void getOrders() {
        System.out.println("Enter the name of the file for order data:");
        String file = input.next();
        try {
            // TODO: Add method
            //data.readOrders(file);
            System.out.println("FEATURE COMING SOON");
        }
        catch (Exception e) {
            System.out.println("--- File not found ---");
            System.out.println();
            getOrders();
        }
    }

    /** Reads from inventory file and initializes the database */
    static void getInventory() {
        System.out.println("Enter the name of the file for inventory data:");
        String file = input.next();
        try {
            // TODO: Add method
            //data.readInventory(file);
            System.out.println("FEATURE COMING SOON");
        }
        catch (Exception e) {
            System.out.println("--- File not found ---");
            System.out.println();
            getInventory();
        }
    }
    
    /** Displays all the main user options */
    static void mainMenu() {
        System.out.println("MAIN MENU:");
        System.out.println("m  - View Item Menu");
        System.out.println("o  - New Order");
        System.out.println("ro - Remove Order");
        System.out.println("uo - Update Order");
        System.out.println("c  - New Customer");
        System.out.println("uc - Update Customer");
        System.out.println("i  - Add Inventory Item");
        System.out.println("ri - Remove Inventory Item");
        System.out.println("ui - Update Inventory Item");
        System.out.println("v  - View Info");
        System.out.println("s  - Output Database to File");
        System.out.println("q  - Quit the System");
        
        String cmd = input.next();
        // View Item Menu
        if (cmd.equals("m")) {
            System.out.println();
            viewInventory();
        }
        // TODO:    Move this inside new order?
        //          to ensure every customer has an order?
        // New Customer
        else if (cmd.equals("c")) {
            System.out.println();
            addCustomer();
        }
        // Update Customer
        else if (cmd.equals("uc")) {
            System.out.println();
            updateCustomer();
        }
        // Add Order
        else if (cmd.equals("o")) {
            System.out.println();
            addOrder(0);
        }
        // Remove order
        else if (cmd.equals("ro")) {
            System.out.println();
            removeOrder();
        }
        // Update order
        else if (cmd.equals("uo")) {
            System.out.println();
            updateOrder();
        }
        // Add Inventory Item
        else if (cmd.equals("i")) {
            System.out.println();
            addItem(0);
        }
        // Remove Inventory Item
        else if (cmd.equals("ri")) {
            System.out.println();
            removeItem();
        }
        // Update Inventory Item
        else if (cmd.equals("ui")) {
            System.out.println();
            updateItem();
        }
        // Open Info Menu
        else if (cmd.equals("v")) {
            System.out.println();
            infoMenu();
        }
        // Quit the program and save to file
        else if (cmd.equals("q")) {
            quit = true;
        }
        else {
            System.out.println("--- Invalid Command ---");
            System.out.println();
        }
    }
    
    /** Displays the current inventory for the customer */
    static void viewInventory() {
        System.out.println("All Items Available for Order:");
        data.printInventory();
        System.out.println();
        System.out.println("Press Enter to Continue...");
        input.next();
    }
    
    /** Prompts for each field of a new customer and adds it to data 
     *  @return The customer that is created
     */
    static Customer addCustomer() {
        int id = 0;
        String name, address, city, state, zipcode;
        while (data.hasCustomer(id)) {
            id += 1;
        }
        System.out.println("Enter new Customer Name (Full name):");
        try {
            name = input.next();
            if (data.hasCustomer(name)) {
                System.out.println("--- Name Already In Database ---");
                return addCustomer();
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer Address:");
        try {
            address = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer City:");
        try {
            city = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer State:");
        try {
            state = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer Zipcode:");
        try {
            zipcode = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        Customer c = 
                new Customer(id, name, address, city, state, zipcode, 0, 0);
        data.addCustomer(c);
        System.out.println(name + " added successfully.");
        return c;
    }
    
    /** Prompts for a customer and replaces it with an updated one */
    static void updateCustomer() {
        System.out.println("Enter name of customer to update:");
        String oldName = input.next();
        if (data.hasCustomer(oldName)) {
            data.removeCustomer(oldName);
            addCustomer();
        }
        else {
            System.out.println("--- Customer not in database. ---");
            System.out.println();
        }
    }
    
    /**
     * Adds a new order to the database
     * @param orderId The number to try first for order id
     */
    static void addOrder(int orderId) {
        while (data.hasOrder(orderId)) {
            orderId += 1;
        }
        System.out.println("Is this order for an existing customer? (y/n)");
        String cmd = input.next();
        Customer c;
        if (cmd.equals("y")) {
            System.out.println("Enter Full Customer Name:");
            try {
                c = data.getCustomer(input.next());
            }
            catch (Exception e) {
                System.out.println("--- Customer Not Found ---");
                return;
            }
        }
        else if (cmd.equals("n")) {
            c = addCustomer();
        }
        else {
            System.out.println("--- Invalid Input ---");
            return;
        }
        
        System.out.println();
        // TODO: CLarify order representation
        HashMap<Item, Integer> total = data.getItems(orderId);
        printReceipt(total);
        /* =============== Receipt: ===============
         * ORDER ID     DATE        PICKUP DATE
         * CUSTOMER NAME
         * -- ITEM              AMOUNT      PRICE
         * -- ITEM              AMOUNT      PRICE
         * ----------------------------------------
         * TOTAL PRICE
         * PAID?
         * REMAINING LOYALTY CREDIT
         * ========================================
         */
    }
    
    /** Prints the information about the order */
    static void printReceipt(HashMap<Item, Integer> list) {
        // TODO: Add body
    }
    
    /** Removes the order with the given id */
    static void removeOrder() {
        System.out.println("Enter ID number of order to change:");
        try {
            int oldId = input.nextInt();
            if (data.hasOrder(oldId)) {
                data.removeOrder(oldId);
                System.out.println("Order " + oldId + 
                        " successfully removed.");
            }
            else {
                System.out.println("--- No Order with that ID ---");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
    }
    
    /** Replaces the order with the given ID with a new one */
    static void updateOrder() {
        System.out.println("Enter ID number of order to change:");
        try {
            int oldId = input.nextInt();
            if (data.hasOrder(oldId)) {
                data.removeOrder(oldId);
                addOrder(oldId);
            }
            else {
                System.out.println("--- No Order with that ID ---");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
    }
    
    /** Adds the item to the inventory 
     *  @param id The id to check first
     *  @return The new item
     */
    static Item addItem(int id) {
        int itemId = 0;
        String name, category;
        double price;
        while (data.hasItem(itemId)) {
            itemId += 1;
        }
        System.out.println("Enter new Item Name:");
        try {
            name = input.next();
          //TODO Items can have the same name as long as they are in different categories. See Strawberry Cream.
//            if (data.inventory.containsKey(name)) {
//                System.out.println("--- Item Name Already In Use ---");
//                return addItem(id);
//            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addItem(id);
        }
        System.out.println("Enter new Item Category:");
        try {
            category = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addItem(id);
        }
        System.out.println("Enter new Item Price (double):");
        try {
            price = input.nextDouble();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addItem(id);
        }
        
        Item i = new Item(itemId, name, category, price);
        data.addItem(i);
        return i;
    }
    
    /** Remove the given item from the inventory */
    static void removeItem() {
        System.out.println("Enter name of item to remove from inventory:");
        try {
            String name = input.next();
            if (data.inventory.containsKey(name)) { //TODO Gonna need a category also, items can have same name.
                data.removeItem(name);
                System.out.println("Item " + name + 
                        " successfully removed.");
            }
            else {
                System.out.println("--- No Item with that Name ---");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
    }
    
    /** Replaces the given item with a new one */
    static void updateItem() {
        System.out.println("Enter name of item to update:");
        try {
            String name = input.next();
            if (data.inventory.containsKey(name)) {
                int newId = data.orders.remove(name).id;
                addItem(newId);
            }
            else {
                System.out.println("--- No Item with that Name ---");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
    }
    
    /** Displays menu options for displaying various info */
    static void infoMenu() {
        System.out.println("What information do you want to display?:");
        System.out.println("c  - Specific customer information");
        System.out.println("oc - All orders by a specific customer");
        System.out.println("op - All orders placed on a specific date");
        System.out.println("of - All orders finished on a specific date");
        System.out.println("oi - All orders of a specific item");
        System.out.println("ou - All unpaid orders");
        
        // TODO: add code
    }
}
