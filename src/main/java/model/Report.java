package model;

import java.util.List;

public class Report {
    private String branchCode;
    private List<Sale> salesData;
    private List<Product> stockData;
    private List<Double> profitData;

    // Getters and Setters
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<Sale> getSalesData() {
        return salesData;
    }

    public void setSalesData(List<Sale> salesData) {
        this.salesData = salesData;
    }

    public List<Product> getStockData() {
        return stockData;
    }

    public void setStockData(List<Product> stockData) {
        this.stockData = stockData;
    }

    public List<Double> getProfitData() {
        return profitData;
    }

    public void setProfitData(List<Double> profitData) {
        this.profitData = profitData;
    }
}