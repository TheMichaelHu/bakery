import java.util.HashMap;

public class Customer {
    String name;
    String address;
    String phone;
    int creditCard;
    HashMap<String, Order> orders;
    int points;
    boolean isLoyal;
    
    Customer(String n, String a, String p, int c, boolean l) {
        this.name = n;
        this.address = a;
        this.phone = p;
        this.creditCard = c;
        this.orders = new HashMap<String, Order>();
        this.points = 0;
        this.isLoyal = l;
    }
    
    void addPoints(int p) {
        this.points += p;
    }
}
