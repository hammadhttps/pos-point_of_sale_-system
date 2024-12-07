package model;

import java.util.List;
import java.util.Date;

public class Sale {
    private String saleId;
    private List<Product> products;
    private double totalAmount;
    private Date date;// Use Date object instead of String
    private String branchCode;

    public Sale(String id, List<Product> products, double totalAmount, Date date,String branchCode1) {
        this.products = products;
        this.saleId = id;
        this.totalAmount = totalAmount;
        this.date = date;
        this.branchCode=branchCode1;
    }

    public void setBranchCode(String branchCode1)
    {
        this.branchCode=branchCode1;
    }
    public String getBranchCode()
    {
        return branchCode;
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
