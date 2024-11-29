package model;

import java.util.List;
import java.util.Date;

public class Sale {
    private String saleId;
    private List<Product> products;
    private double totalAmount;
    private Date date; // Use Date object instead of String

    public Sale(String id, List<Product> products, double totalAmount, Date date) {
        this.products = products;
        this.saleId = id;
        this.totalAmount = totalAmount;
        this.date = date;
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
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
