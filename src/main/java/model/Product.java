package model;

import java.util.Objects;

/**
 * Represents a product in the POS system.
 * Contains product information including pricing, inventory, and
 * categorization.
 */
public class Product {

    // Instance variables
    private String productId;
    private String name;
    private String category;
    private double originalPrice;
    private double salePrice;
    private double priceByUnit;
    private double priceByCarton;
    private int quantity;
    private int soldQuantity;

    // Constants
    private static final double MIN_PRICE = 0.0;
    private static final int MIN_QUANTITY = 0;

    /**
     * Default constructor
     */
    public Product() {
        this.soldQuantity = 0;
    }

    /**
     * Constructor with all product details
     * 
     * @param productId     unique identifier for the product
     * @param name          product name
     * @param category      product category
     * @param originalPrice original price of the product
     * @param salePrice     sale price of the product
     * @param priceByUnit   price per unit
     * @param priceByCarton price per carton
     * @param quantity      available quantity in stock
     */
    public Product(String productId, String name, String category,
            double originalPrice, double salePrice, double priceByUnit,
            double priceByCarton, int quantity) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.originalPrice = validatePrice(originalPrice, "Original price");
        this.salePrice = validatePrice(salePrice, "Sale price");
        this.priceByUnit = validatePrice(priceByUnit, "Price by unit");
        this.priceByCarton = validatePrice(priceByCarton, "Price by carton");
        this.quantity = validateQuantity(quantity);
        this.soldQuantity = 0;
    }

    /**
     * Constructor for product with minimal information
     * 
     * @param productId unique identifier for the product
     * @param quantity  available quantity in stock
     */
    public Product(String productId, int quantity) {
        this.productId = productId;
        this.quantity = validateQuantity(quantity);
        this.soldQuantity = 0;
    }

    /**
     * Constructor for product with basic information
     * 
     * @param productId unique identifier for the product
     * @param name      product name
     * @param quantity  available quantity in stock
     */
    public Product(String productId, String name, int quantity) {
        this.productId = productId;
        this.name = name;
        this.quantity = validateQuantity(quantity);
        this.soldQuantity = 0;
    }

    // Validation methods
    private double validatePrice(double price, String fieldName) {
        if (price < MIN_PRICE) {
            throw new IllegalArgumentException(fieldName + " cannot be negative");
        }
        return price;
    }

    private int validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return quantity;
    }

    // Business logic methods
    /**
     * Check if product is in stock
     * 
     * @return true if quantity > 0, false otherwise
     */
    public boolean isInStock() {
        return quantity > 0;
    }

    /**
     * Check if product is low in stock (less than 10 units)
     * 
     * @return true if quantity < 10, false otherwise
     */
    public boolean isLowStock() {
        return quantity < 10;
    }

    /**
     * Calculate the profit margin percentage
     * 
     * @return profit margin as percentage, or 0 if original price is 0
     */
    public double getProfitMargin() {
        if (originalPrice <= 0) {
            return 0.0;
        }
        return ((salePrice - originalPrice) / originalPrice) * 100;
    }

    /**
     * Get the total value of current stock
     * 
     * @return total value (quantity * sale price)
     */
    public double getStockValue() {
        return quantity * salePrice;
    }

    /**
     * Update sold quantity for this product
     * 
     * @param soldQuantity quantity sold
     */
    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = validateQuantity(soldQuantity);
    }

    /**
     * Get the quantity sold
     * 
     * @return sold quantity
     */
    public int getSoldQuantity() {
        return soldQuantity;
    }

    // Static factory method for parsing from string
    /**
     * Parse a Product from a comma-separated string
     * 
     * @param line comma-separated string containing product data
     * @return Product object
     * @throws IllegalArgumentException if the string format is invalid
     */
    public static Product parse(String line) {
        try {
            String[] parts = line.split(",");

            if (parts.length != 8) {
                throw new IllegalArgumentException(
                        "Invalid product data format. Expected 8 fields, got " + parts.length);
            }

            String productId = parts[0].trim();
            String name = parts[1].trim();
            String category = parts[2].trim();
            double originalPrice = Double.parseDouble(parts[3].trim());
            double salePrice = Double.parseDouble(parts[4].trim());
            double priceByUnit = Double.parseDouble(parts[5].trim());
            double priceByCarton = Double.parseDouble(parts[6].trim());
            int quantity = Integer.parseInt(parts[7].trim());

            return new Product(productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton,
                    quantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to parse numeric values in product data: " + line, e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse product: " + line, e);
        }
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = validatePrice(originalPrice, "Original price");
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = validatePrice(salePrice, "Sale price");
    }

    public double getPriceByUnit() {
        return priceByUnit;
    }

    public void setPriceByUnit(double priceByUnit) {
        this.priceByUnit = validatePrice(priceByUnit, "Price by unit");
    }

    public double getPriceByCarton() {
        return priceByCarton;
    }

    public void setPriceByCarton(double priceByCarton) {
        this.priceByCarton = validatePrice(priceByCarton, "Price by carton");
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = validateQuantity(quantity);
    }

    // Object methods
    @Override
    public String toString() {
        return String.join(",",
                productId != null ? productId : "",
                name != null ? name : "",
                category != null ? category : "",
                String.valueOf(originalPrice),
                String.valueOf(salePrice),
                String.valueOf(priceByUnit),
                String.valueOf(priceByCarton),
                String.valueOf(quantity));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}