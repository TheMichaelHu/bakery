
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
        input = new Scanner(System.in);
        
        startUp();
        while (!quit) {
            mainMenu();
        }
        
        data.writeToFiles();
    }
    
    /** Displays when starting the system and initializes the database. */
    static void startUp() {
        System.out.println("Welcome to BakeryUI!");
        System.out.println(
                "Enter a command to use the corresponding function:");
        System.out.println("e - Initialize empty database");
        System.out.println("f - Initialize database from files");
        String cmd = input.next();
        input.nextLine(); //Skips over extra input
        // Creates empty database
        if (cmd.equals("e")) {
            data = new Database();
            System.out.println();
        }
        // Creates database from files
        else if (cmd.equals("f")) {
            System.out.println();
            System.out.println("Enter the name of the file for order data:");
            String orders = readString();
            
            System.out.println();
            System.out.println(
                    "Enter the name of the file for inventory data:");
            String inventory = readString();
            
            try {
                data = new Database(orders, inventory);
            }
            catch (Exception e) {
                System.out.println("--- Database Failed to Initialize ---");
                startUp();
            }
        }
        else {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            startUp();
        }
    }
    
    /** Displays all the main user options */
    static void mainMenu() {
        System.out.println("MAIN MENU:");
        System.out.println("m  - View Item Menu");
        System.out.println("o  - New Order");
        System.out.println("ro - Remove Order");
        System.out.println("uo - Update Order");
        System.out.println("uc - Update Customer");
        System.out.println("i  - Add Inventory Item");
        System.out.println("ri - Remove Inventory Item");
        System.out.println("ui - Update Inventory Item");
        System.out.println("v  - View Info");
        System.out.println("s  - Output Database to File");
        System.out.println("q  - Quit the System");
        
        String cmd = input.next();
        input.nextLine(); //Skips over extra input
        // View Item Menu
        if (cmd.equals("m")) {
            System.out.println();
            viewInventory();
        }
        // Update Customer
        else if (cmd.equals("uc")) {
            System.out.println();
            updateCustomer();
        }
        // Add Order
        else if (cmd.equals("o")) {
            System.out.println();
            addOrder();
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
            addItem();
        }
        // Remove Inventory Item
        else if (cmd.equals("ri")) {
            System.out.println();
            System.out.println("FEATURE COMING SOON");
            //removeItem();
        }
        // Update Inventory Item
        else if (cmd.equals("ui")) {
            System.out.println();
            System.out.println("FEATURE COMING SOON");
            //updateItem();
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
        System.out.println("Enter any Key to Continue...");
        input.next();
        input.nextLine(); //Skips over extra input
    }
    
    /** Prompts for each field of a new customer and adds it to data 
     *  @return The name of the customer that is created
     */
    static String addCustomer() {
        String name, address, city, state, zipcode;
        System.out.println("Enter new Customer Name (Full name):");
        try {
            name = readString();
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
            address = readString();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer City:");
        try {
            city = readString();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer State:");
        try {
            state = readString();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer Zipcode:");
        try {
            zipcode = input.next();
            input.nextLine(); //Skips over extra input
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }

        data.addCustomer(name, address, city, state, zipcode);
        System.out.println(name + " added successfully.");
        return name;
    }
    
    /** Prompts for a customer and replaces it with an updated one */
    static void updateCustomer() {
        System.out.println("Enter name of customer to update:");
        String oldName = readString();
        if (!data.hasCustomer(oldName)) {
            System.out.println("--- Customer not in database. ---");
            return;
        }
        
        String name, address, city, state, zipcode;
        System.out.println("Enter new Customer Name (Full name):");
        try {
            name = readString();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        System.out.println("Enter new Customer Address:");
        try {
            address = readString();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        System.out.println("Enter new Customer City:");
        try {
            city = readString();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        System.out.println("Enter new Customer State:");
        try {
            state = readString();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        System.out.println("Enter new Customer Zipcode:");
        try {
            zipcode = input.next();
            input.nextLine(); //Skips over extra input
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        
        data.updateCustomer(oldName, name, address, city, state, zipcode);
        System.out.println(name + " updated successfully.");
    }
    
    /** Adds a new order to the database */
    static void addOrder() {
        System.out.println("Is this order for an existing customer? (y/n)");
        String cmd = input.next();
        input.nextLine(); // Skips extra input
        
        String name;
        if (cmd.equals("y")) {
            System.out.println("Enter Full Customer Name:");
            try {
                name = readString();
                data.getCustomer(name);
            }
            catch (Exception e) {
                System.out.println("--- Customer Not Found ---");
                return;
            }
        }
        else if (cmd.equals("n")) {
            name = addCustomer();
        }
        else {
            System.out.println("--- Invalid Input ---");
            return;
        }
        
        System.out.println();
        HashMap<Integer, Integer> list = new HashMap<Integer, Integer>();
        // TODO: ADD BODY
        // TODO: add printReceipt method to data.addOrder
        data.addOrder(name, list);
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
        
    /** Removes the order with the given id */
    static void removeOrder() {
        System.out.println("Enter ID number of order to remove:");
        try {
            int id = input.nextInt();
            if (data.hasOrder(id)) {
                data.removeOrder(id);
                System.out.println("Order " + id + 
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
    
    /** Adds the item to the inventory */
    static void addItem() {
        String name, category;
        double price;
        System.out.println("Enter new Item Name:");
        try {
            name = readString();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addItem();
            return;
        }
        System.out.println("Enter new Item Category:");
        try {
            category = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addItem();
            return;
        }
        System.out.println("Enter new Item Price (double):");
        try {
            price = input.nextDouble();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addItem();
            return;
        }
        
        data.addItem(name, category, price);
    }
    
    /** Remove the given item from the inventory */
    static void removeItem() {
        System.out.println("Enter ID of item to remove from inventory:");
        try {
            int id = input.nextInt();
            input.nextLine(); // Skips over extra input
            if (data.hasItem(id)) {
                data.removeItem(id);
                System.out.println("Item " + id + 
                        " successfully removed.");
            }
            else {
                System.out.println("--- No Item with that ID ---");
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
        System.out.println("x  - Return to Main Menu");
        
        String cmd = input.next();
        input.nextLine(); // Skips over extra input
        if (cmd.equals("c")) {
            System.out.println();
            customerInfo();
            infoMenu();
        }
        else if (cmd.equals("oc")) {
            System.out.println();
            customerOrders();
            infoMenu();
        }
        else if (cmd.equals("op")) {
            System.out.println();
            placedDateOrders();
            infoMenu();
        }
        else if (cmd.equals("of")) {
            System.out.println();
            finishedDateOrders();
            infoMenu();
        }
        else if (cmd.equals("oi")) {
            System.out.println();
            itemOrders();
            infoMenu();
        }
        else if (cmd.equals("ou")) {
            System.out.println();
            data.printUnpaidOrders();
            infoMenu();
        }
        else if (cmd.equals("x")) {
            System.out.println();
        }
        else {
            System.out.println("--- Invalid Command ---");
            System.out.println();
            infoMenu();
        }
    }
    
    
    
    /** Method used to get an entire string from the console
     *  regardless of spaces
     * @return The string from the console
     */
    static String readString() {
        String ret;
        ret = input.next();
        while (input.hasNext()) {
            ret = ret + " " + input.next();
        }
        return ret;
    }
}
