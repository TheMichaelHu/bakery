import java.util.Scanner;


public class BakeryUI {
    
    static boolean quit = false;
    public static void main(String[] args) {
        /* TODO: Add functionality for:
         * Add new customers
         * Update customer info
         * Add order (and print receipt)
         * Update orders (change amount, etc.)
         * Add bakery item
         * Update bakery item
         * 
         * TODO: Add employee viewing functions
         * 
         * TODO: Must track:
         * Customer information
         *      same information as in given file
         *      contact
         *      loyalty card
         *          total towards next discount
         *          remaining discount amount
         * Order information
         * Available inventory
         *      same fields as given file
         */
        Database data = new Database();
        Scanner input;
        
        input = new Scanner(System.in);
        
        startUp(input, data);
        while (!quit) {
            mainMenu(input, data);
        }
        
    }
    
    /**
     * Displays when starting the system.
     * Initializes the database.
     * @param input The input from the console
     * @param data The database
     */
    static void startUp(Scanner input, Database data) {
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
            getOrders(input, data);
            System.out.println();
            getInventory(input, data);
            System.out.println();
        }
        else {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            startUp(input, data);
        }
    }
    
    /**
     * Reads from orders file and initializes the database
     * @param input The input from the console
     * @param data The database
     */
    static void getOrders(Scanner input, Database data) {
        System.out.println("Enter the name of the file for order data:");
        String file = input.next();
        // TODO: Ensure this handles file-not-found exception (call getFiles again)
        try {
            // TODO: Add method
            //data.readOrders(file);
            System.out.println("FEATURE COMING SOON");
        }
        catch (Exception e) {
            System.out.println("--- File not found ---");
            System.out.println();
            getOrders(input, data);
        }
    }

    /**
     * Reads from inventory file and initializes the database
     * @param input The input from the console
     * @param data The database
     */
    static void getInventory(Scanner input, Database data) {
        System.out.println("Enter the name of the file for inventory data:");
        String file = input.next();
        // TODO: Handle exceptions
        try {
            // TODO: Add method
            //data.readInventory(file);
            System.out.println("FEATURE COMING SOON");
        }
        catch (Exception e) {
            System.out.println("--- File not found ---");
            System.out.println();
            getInventory(input, data);
        }
    }
    
    /**
     * 
     * @param input
     * @param data
     */
    static void mainMenu(Scanner input, Database data) {
        System.out.println("MAIN MENU:");
        System.out.println("c  - New Customer");
        System.out.println("uc - Update Customer");
        System.out.println("o  - New Order");
        System.out.println("uo - Update Order");
        System.out.println("i  - Add Inventory Item");
        System.out.println("ui - Update Inventory Item");
        System.out.println("v  - View Info");
        System.out.println("s  - Output Database to File");
        System.out.println("q  - Quit the System");
        
        String cmd = input.next();
        if (cmd.equals("c")) {
            System.out.println();
            addCustomer(input, data);
        }
        else if (cmd.equals("uc")) {
            System.out.println("Enter name of customer to update:");
            String oldName = input.next();
            if (data.customers.containsKey(oldName)) {
                data.removeCustomer(oldName);
                addCustomer(input, data);
            }
            else {
                System.out.println("Customer not in database.");
                System.out.println();
            }
        }
        else if (cmd.equals("o")) {
            // NEW ORDER
        }
        else if (cmd.equals("uo")) {
            // UPDATE ORDER
        }
        else if (cmd.equals("i")) {
            // ADD INVENTORY ITEM
        }
        else if (cmd.equals("ui")) {
            // UPDATE INVENTORY ITEM
        }
        else if (cmd.equals("v")) {
            // VIEW INFO
        }
        else if (cmd.equals("s")) {
            // SAVE TO FILE
        }
        else if (cmd.equals("q")) {
            quit = true;
        }
        else {
            System.out.println("--- Invalid Command ---");
            System.out.println();
            mainMenu(input, data);
        }
    }
    
    /**
     * Prompts for each field of a new customer and adds it to data
     * @param input The console input
     * @param data The database
     */
    static void addCustomer(Scanner input, Database data) {
        int id;
        String name, address, city, state, zipcode;
        System.out.println("Enter new Customer ID (integer):");
        try {
            id = input.nextInt();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addCustomer(input, data);
            return;
        }
        System.out.println("Enter new Customer Name (Full name):");
        try {
            name = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addCustomer(input, data);
            return;
        }
        System.out.println("Enter new Customer Address:");
        try {
            address = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addCustomer(input, data);
            return;
        }
        System.out.println("Enter new Customer City:");
        try {
            city = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addCustomer(input, data);
            return;
        }
        System.out.println("Enter new Customer State:");
        try {
            state = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addCustomer(input, data);
            return;
        }
        System.out.println("Enter new Customer Zipcode:");
        try {
            zipcode = input.next();
        }
        catch (Exception e) {
            System.out.println("--- Invalid Input ---");
            System.out.println();
            addCustomer(input, data);
            return;
        }
        
        data.addCustomer(id, name, address, city, state, false, 0, 0);
        System.out.println(name + " added successfully.");
    }
    
    
}
