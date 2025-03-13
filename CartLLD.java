import java.util.*;

interface PaymentStrategy {
    void printPaymentStrategy(double totalAmount);
}

class PayPal implements PaymentStrategy {
    @Override
    public void printPaymentStrategy(double totalAmount) {
        System.out.println("Processing PayPal payment for amount: $" + totalAmount);
    }
}

class CreditCard implements PaymentStrategy {
    @Override
    public void printPaymentStrategy(double totalAmount) {
        System.out.println("Processing Credit Card payment for amount: $" + totalAmount);
    }
}

class PaymentFactory {
    public static PaymentStrategy getPaymentStrategy(String type) {
        if ("CreditCard".equalsIgnoreCase(type)) {
            return new CreditCard();
        } else if ("PayPal".equalsIgnoreCase(type)) {
            return new PayPal();
        } else {
            throw new IllegalArgumentException("Invalid payment type");
        }
    }





class Inventory 
{
    private Map<String, Integer> stock;

    public Inventory() {
        this.stock = new HashMap<>();
        stock.put("Laptop", 5);
        stock.put("Mouse", 10);
        stock.put("Keyboard", 7);
    }

    public void updateStock(List<String> items) 
    {
        for (String item : items) {
            if (stock.containsKey(item)) {
                int stockCount = stock.get(item);
                if (stockCount > 0) {
                    stock.put(item, stockCount - 1);
                } else {
                    System.out.println("Item " + item + " is out of stock.");
                }
            } else {
                System.out.println("Item " + item + " not found in inventory.");
            }
        }
    }
}

class Shipping {
    public void shipItems(List<String> items) {
        System.out.println("Shipping items: " + items);
    }
}



// Order Class with Dependency Injection
class Order {
    private Inventory inventory;
    private Shipping shipping;
    private PaymentStrategy paymentStrategy;
    private List<String> items;
    private List<Double> prices;
    private double totalAmount;

    public Order(Inventory inventory, Shipping shipping, PaymentStrategy paymentStrategy) 
    {
        this.inventory = inventory;
        this.shipping = shipping;
        this.paymentStrategy = paymentStrategy;
        this.items = new ArrayList<>();
        this.prices = new ArrayList<>();
        this.totalAmount = 0;
    }

    public void addItem(String item, double price) {
        items.add(item);
        prices.add(price);
        totalAmount += price;
    }

    public void processOrder() 
    {
        paymentStrategy.printPaymentStrategy(totalAmount);

        inventory.updateStock(items);

        shipping.shipItems(items);
    }
}

public class Solution 
{
    public static void main(String[] args) 
    {
        // Simulating Dependency Injection (DI)
        Inventory inventory = new Inventory();
        Shipping shipping = new Shipping();
        PaymentStrategy paymentStrategy = PaymentFactory.getPaymentStrategy("CreditCard"); // Swap with new PayPal() if needed

        // Inject dependencies into Order
        Order order = new Order(inventory, shipping, paymentStrategy);
        order.addItem("Laptop", 1500);
        order.addItem("Mouse", 25);
        order.processOrder();
    }
}
