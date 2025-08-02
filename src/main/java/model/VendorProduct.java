package model;

import java.util.Objects;

/**
 * Represents a vendor product in the POS system.
 * Contains product information specific to vendor relationships.
 */
public class VendorProduct {
    
    // Instance variables
    private String productId;
    private String name;
    private String category;
    private double originalPrice;
    private double salePrice;
    private double priceByUnit;
    private double priceByCarton;
    private int quantity;
    
    // Constants
    private static final double MIN_PRICE = 0.0;
    private static final int MIN_QUANTITY = 0;
    
    /**
     * Default constructor
     */
    public VendorProduct() {
    }
    
    /**
     * Constructor with all product details
     * 
     * @param productId unique identifier for the product
     * @param name product name
     * @param category product category
     * @param originalPrice original price of the product
     * @param salePrice sale price of the product
     * @param priceByUnit price per unit
     * @param priceByCarton price per carton
     * @param quantity available quantity in stock
     */
    public VendorProduct(String productId, String name, String category, 
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
    }
    
    /**
     * Constructor for product with basic information
     * 
     * @param productId unique identifier for the product
     * @param name product name
     * @param quantity available quantity in stock
     */
    public VendorProduct(String productId, String name, int quantity) {
        this.productId = productId;
        this.name = name;
        this.quantity = validateQuantity(quantity);
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
        return String.format("VendorProduct{id='%s', name='%s', category='%s', quantity=%d}",
                productId, name, category, quantity);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VendorProduct that = (VendorProduct) obj;
        return Objects.equals(productId, that.productId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}