package model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a sale transaction in the POS system.
 * Contains information about the sale including products, amounts, and
 * metadata.
 */
public class Sale {

    // Instance variables
    private String saleId;
    private List<Product> products;
    private double totalAmount;
    private Date date;
    private String branchCode;
    private int quantity;

    // Constants
    private static final double MIN_AMOUNT = 0.0;
    private static final int MIN_QUANTITY = 0;

    /**
     * Constructor with all sale details
     * 
     * @param saleId      unique identifier for the sale
     * @param products    list of products in the sale
     * @param quantity    total quantity of items sold
     * @param totalAmount total amount of the sale
     * @param date        date of the sale
     * @param branchCode  branch code where sale occurred
     */
    public Sale(String saleId, List<Product> products, int quantity,
            double totalAmount, Date date, String branchCode) {
        this.saleId = saleId;
        this.products = products;
        this.quantity = validateQuantity(quantity);
        this.totalAmount = validateAmount(totalAmount);
        this.date = date != null ? new Date(date.getTime()) : new Date();
        this.branchCode = branchCode;
    }

    /**
     * Constructor without quantity (quantity will be calculated from products)
     * 
     * @param saleId      unique identifier for the sale
     * @param products    list of products in the sale
     * @param totalAmount total amount of the sale
     * @param date        date of the sale
     * @param branchCode  branch code where sale occurred
     */
    public Sale(String saleId, List<Product> products, double totalAmount,
            Date date, String branchCode) {
        this.saleId = saleId;
        this.products = products;
        this.totalAmount = validateAmount(totalAmount);
        this.date = date != null ? new Date(date.getTime()) : new Date();
        this.branchCode = branchCode;
        this.quantity = calculateTotalQuantity();
    }

    // Validation methods
    private double validateAmount(double amount) {
        if (amount < MIN_AMOUNT) {
            throw new IllegalArgumentException("Total amount cannot be negative");
        }
        return amount;
    }

    private int validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return quantity;
    }

    // Business logic methods
    /**
     * Calculate total quantity from products
     * 
     * @return total quantity of all products
     */
    private int calculateTotalQuantity() {
        if (products == null) {
            return 0;
        }
        return products.stream()
                .mapToInt(Product::getSoldQuantity)
                .sum();
    }

    /**
     * Get the number of different products in this sale
     * 
     * @return number of unique products
     */
    public int getProductCount() {
        return products != null ? products.size() : 0;
    }

    /**
     * Check if this sale has any products
     * 
     * @return true if sale has products, false otherwise
     */
    public boolean hasProducts() {
        return products != null && !products.isEmpty();
    }

    /**
     * Calculate the average price per item
     * 
     * @return average price, or 0 if no quantity
     */
    public double getAveragePricePerItem() {
        if (quantity <= 0) {
            return 0.0;
        }
        return totalAmount / quantity;
    }

    /**
     * Get a formatted string representation of the sale date
     * 
     * @return formatted date string
     */
    public String getFormattedDate() {
        if (date == null) {
            return "N/A";
        }
        return date.toString();
    }

    // Getters and Setters
    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        // Recalculate quantity when products change
        this.quantity = calculateTotalQuantity();
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = validateAmount(totalAmount);
    }

    public Date getDate() {
        return date != null ? new Date(date.getTime()) : null;
    }

    public void setDate(Date date) {
        this.date = date != null ? new Date(date.getTime()) : new Date();
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
        return String.format("Sale{id='%s', amount=%.2f, date=%s, branch='%s', items=%d}",
                saleId, totalAmount, getFormattedDate(), branchCode, quantity);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Sale sale = (Sale) obj;
        return Objects.equals(saleId, sale.saleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId);
    }
}
