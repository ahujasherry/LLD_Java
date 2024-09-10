import java.util.*;

// Statement: whenever there is state change in product stock, notify all the consumers... make extensible code, tomorrow it could be any other entity also for state change and consumer type can also differ
// user observer pattern

// Define the Consumer interface
interface Consumer {
    void updateProductInfo(String productName, Boolean inStock);
}

// Implement the ProductConsumer class
class ProductConsumer implements Consumer {
    private String name;
    
    public ProductConsumer(String name) {
        this.name = name;
    }
    
    @Override
    public void updateProductInfo(String productName, Boolean inStock) {
        if (inStock) {
            System.out.println("Dear " + name + ", the product '" + productName + "' is in stock.");
        } else {
            System.out.println("Dear " + name + ", the product '" + productName + "' is out of stock.");
        }
    }
}

// Define the InventoryService interface
interface InventoryService {
    void addConsumer(Consumer consumer);
    void removeConsumer(Consumer consumer);
    void notifyConsumers();
}

// Implement the Product class
class Product implements InventoryService {
    private String productName;
    private Boolean inStock;
    private List<Consumer> consumers;
    
    public Product(String productName, boolean inStock) {
        this.productName = productName;
        this.inStock = inStock;
        this.consumers = new ArrayList<>();
    }
    
    public void setStockStatus(boolean inStock) {
        this.inStock = inStock;
        notifyConsumers();
    }
    
    @Override
    public void addConsumer(Consumer consumer) {
        consumers.add(consumer);
    }

    @Override
    public void removeConsumer(Consumer consumer) {
        consumers.remove(consumer);
    }
    
    @Override
    public void notifyConsumers() {
        for (Consumer c : consumers) {
            c.updateProductInfo(productName, inStock);
        }
    }
}

// Client code to demonstrate the Observer Pattern
public class HelloWorld {
    public static void main(String[] args) {
        // Create a product instance
        Product product = new Product("Call of Duty", true);
        
        // Create consumer instances
        Consumer sherryConsumer = new ProductConsumer("Sherry");
        Consumer amyaConsumer = new ProductConsumer("Amya");
        
        // Register consumers with the product
        product.addConsumer(sherryConsumer);
        product.addConsumer(amyaConsumer);
        
        // Notify consumers about the initial stock status
        product.notifyConsumers();
        
        // Change the product's stock status
        product.setStockStatus(false);
        product.notifyConsumers();
        
        // Change the product's stock status back
        product.setStockStatus(true);
        product.notifyConsumers();
    }
}
