import java.util.ArrayList;
import java.util.Calendar;

public class Order {
    ArrayList<Item> items;
    String date;

    Order(ArrayList<Item> list) {
        this.items = list;
        this.date = Calendar.MONTH + "/" + Calendar.DAY_OF_MONTH + "/"
                + Calendar.YEAR;
    }

    int numItems() {
        return this.items.size();
    }

    double totalCost() {
        int total = 0;
        for (Item i : this.items) {
            total += i.price;
        }
        return total;
    }
}
