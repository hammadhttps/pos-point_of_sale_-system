package model;


import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;

    // Constructor
    public Inventory() {
        products = new HashMap<>();
    }

    // Method to add a new product to the inventory
    public void addProduct(Product product) {
        if (products.containsKey(product.getProductId())) {
            System.out.println("Product already exists in inventory. Use updateProduct to modify it.");
        } else {
            products.put(product.getProductId(), product);
            System.out.println("Product added successfully.");
        }
    }

    // Method to update an existing product
    public void updateProduct(Product product) {
        if (products.containsKey(product.getProductId())) {
            products.put(product.getProductId(), product);
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product not found in inventory. Use addProduct to add it first.");
        }
    }

    // Method to remove a product from the inventory
    public void removeProduct(String productId) {
        if (products.containsKey(productId)) {
            products.remove(productId);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    // Method to fetch a product by its ID
    public Product getProduct(String productId) {
        return products.get(productId);
    }

    // Method to check if a product exists
    public boolean hasProduct(String productId) {
        return products.containsKey(productId);
    }

    // Method to get the total quantity of all products
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (Product product : products.values()) {
            totalQuantity += product.getQuantity();
        }
        return totalQuantity;
    }

    // Method to list all products
    public void listAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("Listing all products:");
            for (Product product : products.values()) {
                System.out.println("ID: " + product.getProductId() + ", Name: " + product.getName() +
                        ", Category: " + product.getCategory() + ", Quantity: " + product.getQuantity());
            }
        }
    }

    // Method to increase the quantity of a product
    public void increaseProductQuantity(String productId, int amount) {
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            product.setQuantity(product.getQuantity() + amount);
            System.out.println("Increased quantity of product ID: " + productId + " by " + amount);
        } else {
            System.out.println("Product not found in inventory.");
        }
    }

    // Method to decrease the quantity of a product
    public void decreaseProductQuantity(String productId, int amount) {
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            if (product.getQuantity() >= amount) {
                product.setQuantity(product.getQuantity() - amount);
                System.out.println("Decreased quantity of product ID: " + productId + " by " + amount);
            } else {
                System.out.println("Not enough stock to decrease by " + amount + ". Current quantity: " + product.getQuantity());
            }
        } else {
            System.out.println("Product not found in inventory.");
        }
    }
}
