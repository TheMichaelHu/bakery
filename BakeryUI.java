import java.util.Scanner;


public class BakeryUI {
    
    public static void main(String[] args) {
        /* TODO: Add functionality for:
         * Construct an empty bakery
         * Initialize bakery from files
         * 
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
        
    }
    
    /**
     * Displays when starting the system.
     * Initializes the database.
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
        }
        else if (cmd.equals("f")) {
            data = 
        }
        else {
            
        }
    }

}
