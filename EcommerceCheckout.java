import java.util.*;

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
}

class ShippingAddress {
    private String street;
    private String city;
    private String country;
    private String postalCode;

    public ShippingAddress(String street, String city, String country, String postalCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getPostalCode() { return postalCode; }
}

class Order_Item {
    private Product product;
    private int quantity;

    public Order_Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
}

interface PaymentStrategy {
    void pay(double amount);
}

class CreditCard implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid using Credit Card: $" + amount);
    }
}

class PayPal implements PaymentStrategy {
    public void pay(double amount) {
        System.out.println("Paid using PayPal: $" + amount);
    }
}

interface DiscountStrategy {
    double applyDiscount(double amount);
}

class FlatDiscount implements DiscountStrategy {
    private double discountAmount;

    public FlatDiscount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double applyDiscount(double amount) {
        return Math.max(amount - discountAmount, 0);
    }
}

class PercentageDiscount implements DiscountStrategy {
    private double discountRate; // e.g., 0.1 for 10%

    public PercentageDiscount(double discountRate) {
        this.discountRate = discountRate;
    }

    public double applyDiscount(double amount) {
        return amount - (amount * discountRate);
    }
}

class Order {
    private int orderId;
    private List<Order_Item> items;
    private double totalPrice;
    private ShippingAddress shippingAddress;

    public Order(int orderId, List<Order_Item> items, ShippingAddress shippingAddress) {
        this.orderId = orderId;
        this.items = items;
        this.shippingAddress = shippingAddress;
    }

    public double calculateTotalPrice() {
        totalPrice = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        return totalPrice;
    }

    public int getOrderId() { return orderId; }
    public List<Order_Item> getItems() { return items; }
    public double getTotalPrice() { return totalPrice; }
    public ShippingAddress getShippingAddress() { return shippingAddress; }
}

class Checkout {
    private Order order;
    private DiscountStrategy discountStrategy;
    private PaymentStrategy paymentStrategy;
    private double deliveryCharge;

    public Checkout(Order order, DiscountStrategy discountStrategy,
                    PaymentStrategy paymentStrategy, double deliveryCharge) {
        this.order = order;
        this.discountStrategy = discountStrategy;
        this.paymentStrategy = paymentStrategy;
        this.deliveryCharge = deliveryCharge;
    }

    public double calculateFinalPrice() {
        double totalPrice = order.calculateTotalPrice();
        double discountedPrice = discountStrategy.applyDiscount(totalPrice);

        // Apply tax (13%)
        double finalPrice = discountedPrice * 1.13;
        finalPrice += deliveryCharge; // Add delivery fee

        return finalPrice;
    }

    public String doCheckout() {
        try {
            double finalAmount = calculateFinalPrice();
            paymentStrategy.pay(finalAmount);

            ShippingAddress address = order.getShippingAddress();
            System.out.println("Shipping to: " + address.getStreet() + ", " 
                    + address.getCity() + ", " + address.getCountry());

            return "Checkout Success!";
        } catch (Exception e) {
            return "Checkout Failed: " + e.getMessage();
        }
    }
}

public class EcommerceCheckout {
    public static void main(String[] args) {
        Product p1 = new Product(1, "Laptop", 1200);
        Product p2 = new Product(2, "Shirt", 50);

        List<Order_Item> items = Arrays.asList(
            new Order_Item(p1, 1),
            new Order_Item(p2, 5)
        );

        ShippingAddress address = new ShippingAddress("123 Main St", "New York", "USA", "10001");

        Order order = new Order(1, items, address);
        double totalPrice = order.calculateTotalPrice();
        System.out.println("Total price of order: $" + totalPrice);

        PaymentStrategy paymentStrategy = new CreditCard();
        DiscountStrategy discountStrategy = new PercentageDiscount(0.10); // 10% discount
        Checkout checkout = new Checkout(order, discountStrategy, paymentStrategy, 10.0);

        double finalPrice = checkout.calculateFinalPrice();
        System.out.println("Final price after discount & tax: $" + finalPrice);

        String result = checkout.doCheckout();
        System.out.println(result);
    }
}
