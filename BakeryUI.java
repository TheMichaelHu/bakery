
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to represent the User Interface of the Bakery System
 * @author Will Enright and Michael Hu
 * @version 6/18/2014
 */
public class BakeryUI {
    
    /** Whether or not to quit the program */
    static boolean quit = false;
    /** The database for the bakery */
    static Database data;
    /** The reader for the console input */
    static BufferedReader input;
    
    /**
     * The main method to start the database  UI
     * @param args Console arguments
     */
    public static void main(String[] args) {
        input = new BufferedReader(new InputStreamReader(System.in));
        
        startUp();
        while (!quit) {
            mainMenu();
        }
        
        data.writeToFiles();
        System.out.println("Bye!");
    }
    
    /** Displays when starting the system and initializes the database. */
    static void startUp() {
        System.out.println("Welcome to BakeryUI!");
        System.out.println(
                "Enter a command to use the corresponding function:");
        System.out.println("e - Initialize empty database");
        System.out.println("f - Initialize database from files");
        String cmd;
        try {
            cmd = input.readLine();
        } 
        catch (IOException e2) {
            startUp();
            return;
        }
        // Creates empty database
        if (cmd.equals("e")) {
            data = new Database();
            System.out.println();
        }
        // Creates database from files
        else if (cmd.equals("f")) {
            System.out.println();
            System.out.println("Enter the name of the file for order data:");
            System.out.println("(Leave off the .txt suffix)");
            String orders;
            try {
                orders = input.readLine();
            } 
            catch (IOException e1) {
                startUp();
                return;
            }
            
            System.out.println();
            System.out.println(
                    "Enter the name of the file for inventory data:");
            System.out.println("(Leave off the .txt suffix)");
            String inventory;
            try {
                inventory = input.readLine();
            } 
            catch (IOException e1) {
                startUp();
                return;                
            }
            
            try {
                data = new Database(orders, inventory, "ord", "inv");
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
        System.out.println("q  - Quit the System");
        
        String cmd;
        try {
            cmd = input.readLine();
        } 
        catch (IOException e) {
            return;
        }
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
        try {
            input.readLine();
        } 
        catch (IOException e) {
            //Return to menu
        }
    }
    
    /** Prompts for each field of a new customer and adds it to data 
     *  @return The name of the customer that is created
     */
    static String addCustomer() {
        /** Customers name */
        String name;
        /** Customers address */
        String address;
        /** Customers city */
        String city;
        /** Customers state */
        String state;
        /** Customers zipcode */
        String zipcode;
        
        System.out.println("Enter new Customer Name (Full name):");
        try {
            name = input.readLine();
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
            address = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer City:");
        try {
            city = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer State:");
        try {
            state = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            return addCustomer();
        }
        System.out.println("Enter new Customer Zipcode:");
        try {
            zipcode = input.readLine();
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
        String oldName;
        try {
            oldName = input.readLine();
        } 
        catch (IOException e1) {
            return;
        }
        if (!data.hasCustomer(oldName)) {
            System.out.println("--- Customer not in database. ---");
            return;
        }
        
        /** Customers name */
        String name;
        /** Customers address */
        String address;
        /** Customers city */
        String city;
        /** Customers state */
        String state;
        /** Customers zipcode */
        String zipcode;
        
        System.out.println("Enter new Customer Name (Full name):");
        try {
            name = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        System.out.println("Enter new Customer Address:");
        try {
            address = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        System.out.println("Enter new Customer City:");
        try {
            city = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        System.out.println("Enter new Customer State:");
        try {
            state = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            updateCustomer();
            return;
        }
        System.out.println("Enter new Customer Zipcode:");
        try {
            zipcode = input.readLine();
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
        String cmd;
        try {
            cmd = input.readLine();
        } 
        catch (IOException e1) {
            return;
        }
        
        String name;
        if (cmd.equals("y")) {
            System.out.println("Enter Full Customer Name:");
            try {
                name = input.readLine();
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
        
        addOrderItems(list);
        
        data.addOrder(name, list);
    }
    
    /**
     * Builds up the list of items in the order
     * @param list The list to build on
     */
    static void addOrderItems(HashMap<Integer, Integer> list) {
        /** The ID of the item */
        int id;
        /** The quantity of the item */
        int count;
        
        System.out.println("Enter ID number of item you want to order:");
        try {
            id = Integer.parseInt(input.readLine());
            if (!data.hasItem(id)) {
                System.out.println("--- No Item with that ID ---");
                addOrderItems(list);
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            addOrderItems(list);
            return;
        }
        System.out.println("Enter the quantity you want to order:");
        try {
            count = Integer.parseInt(input.readLine());
            if (count < 1) {
                System.out.println("--- Must Order at Least 1 Item ---");
                addOrderItems(list);
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            addOrderItems(list);
            return;
        }
        
        list.put(id, count);
        System.out.println("Add another Item? (y/n):");
        try {
            String cmd = input.readLine();
            if (cmd.equals("y")) {
                addOrderItems(list);
                return;
            }
            else {
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
    }
        
    /** Removes the order with the given id */
    static void removeOrder() {
        System.out.println("Enter ID number of order to remove:");
        try {
            int id = Integer.parseInt(input.readLine());
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
        int oldId;
        try {
            oldId = Integer.parseInt(input.readLine());
            if (!data.hasOrder(oldId)) {
                System.out.println("--- No Order with that ID ---");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
        
        System.out.println("Enter Customer Name for this Order:");
        String name;
        try {
            name = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
        
        System.out.println();
        HashMap<Integer, Integer> list = new HashMap<Integer, Integer>();
        addOrderItems(list);
        
        data.updateOrder(oldId, name, list);
    }
    
    /** Adds the item to the inventory */
    static void addItem() {
        /** The name of the item */
        String name;
        /** The category of the item */
        String category;
        /** The price of the item */
        double price;
        
        System.out.println("Enter new Item Name:");
        try {
            name = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addItem();
            return;
        }
        System.out.println("Enter new Item Category:");
        try {
            category = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addItem();
            return;
        }
        System.out.println("Enter new Item Price (double):");
        try {
            price = Double.parseDouble(input.readLine());
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
            int id = Integer.parseInt(input.readLine());
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
        System.out.println("Enter ID of item to update:");
        int id;
        try {
            id = Integer.parseInt(input.readLine());
            if (!data.hasItem(id)) {
                System.out.println("--- No Item with that ID ---");
                return;
            }
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
        
        /** The name of the item */
        String name;
        /** The category of the item */
        String category;
        
        try {
            System.out.println("Enter name of item:");
            name = input.readLine();
            System.out.println("Enter name of item category:");
            category = input.readLine();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
        
        /** The price of the item */
        Double price;
        try {
            System.out.println("Enter price:");
            price = Double.parseDouble(input.readLine());
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            return;
        }
        
        data.updateItem(id, name, category, price);
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
        
        String cmd;
        try {
            cmd = input.readLine();
        } 
        catch (IOException e) {
            infoMenu();
            return;
        }
        // Specific customer information
        if (cmd.equals("c")) {
            System.out.println();
            System.out.println("Enter the full name of the customer:");
            String name;
            try {
                name = input.readLine();
            } 
            catch (IOException e) {
                infoMenu();
                return;
            }
            if (data.hasCustomer(name)) {
                data.printCustomer(name);
            }
            else {
                System.out.println("--- Customer name not in database ---");
                infoMenu();
            }
            System.out.println("Press Enter to Continue...");
            try {
                input.readLine();
            } 
            catch (IOException e) {
                // Return to menu
            }
            infoMenu();
        }
        // Orders for a certain customer
        else if (cmd.equals("oc")) {
            System.out.println();
            System.out.println("Enter the full name of the customer:");
            String name;
            try {
                name = input.readLine();
            } 
            catch (IOException e) {
                infoMenu();
                return;
            }
            if (data.hasCustomer(name)) {
                data.printCustomerOrders(name);
            }
            else {
                System.out.println("--- Customer name not in database ---");
                infoMenu();
            }
            System.out.println("Press Enter to Continue...");
            try {
                input.readLine();
            } 
            catch (IOException e) {
                // Return to menu
            }
            infoMenu();
        }
        // Orders placed on a certain date
        else if (cmd.equals("op")) {
            System.out.println();
            System.out.println("Enter the Date to search for (MM/DD/YYYY):");
            String date;
            try {
                date = input.readLine();
            } 
            catch (IOException e) {
                infoMenu();
                return;
            }
            data.printPlacedDateOrders(date);
            System.out.println("Press Enter to Continue...");
            try {
                input.readLine();
            } 
            catch (IOException e) {
                // Return to menu
            }
            infoMenu();
        }
        // Orders completed/picked up on a certain date
        else if (cmd.equals("of")) {
            System.out.println();
            System.out.println("Enter the Date to search for (MM/DD/YYYY):");
            String date;
            try {
                date = input.readLine();
            } 
            catch (IOException e) {
                infoMenu();
                return;
            }
            data.printFinishedDateOrders(date);
            System.out.println("Press Enter to Continue...");
            try {
                input.readLine();
            } 
            catch (IOException e) {
                // Return to menu
            }
            infoMenu();
        }
        // Orders of a particular item
        else if (cmd.equals("oi")) {
            System.out.println();
            System.out.println("Enter the Item ID to search for:");
            int itemId;
            try {
                itemId = Integer.parseInt(input.readLine());
            } 
            catch (NumberFormatException e) {
                infoMenu();
                return;
            } 
            catch (IOException e) {
                infoMenu();
                return;
            }
            System.out.println("Orders of Item " + itemId + ":");
            data.printItemOrders(itemId);
            infoMenu();
        }
        // Unpaid Orders
        else if (cmd.equals("ou")) {
            System.out.println();
            System.out.println("Unpaid Orders:");
            data.printUnpaidOrders();
            System.out.println("Press Enter to Continue...");
            try {
                input.readLine();
            } 
            catch (IOException e) {
                // Return to menu
            }
            infoMenu();
        }
        // Exit to main menu
        else if (cmd.equals("x")) {
            System.out.println();
        }
        else {
            System.out.println("--- Invalid Command ---");
            System.out.println();
            infoMenu();
        }
    }
}
