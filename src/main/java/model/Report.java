package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents a report in the POS system.
 * Contains aggregated data for reporting purposes including sales, stock, and
 * profit information.
 */
public class Report {

    // Instance variables
    private String reportId;
    private String branchCode;
    private Date reportDate;
    private String reportType;
    private List<Sale> salesData;
    private List<Product> stockData;
    private List<Double> profitData;

    // Constants
    private static final String DEFAULT_REPORT_TYPE = "GENERAL";

    /**
     * Default constructor
     */
    public Report() {
        this.reportDate = new Date();
        this.reportType = DEFAULT_REPORT_TYPE;
        this.salesData = new ArrayList<>();
        this.stockData = new ArrayList<>();
        this.profitData = new ArrayList<>();
    }

    /**
     * Constructor with basic report information
     * 
     * @param reportId   unique identifier for the report
     * @param branchCode branch code for the report
     * @param reportType type of report
     */
    public Report(String reportId, String branchCode, String reportType) {
        this.reportId = reportId;
        this.branchCode = branchCode;
        this.reportType = reportType != null ? reportType : DEFAULT_REPORT_TYPE;
        this.reportDate = new Date();
        this.salesData = new ArrayList<>();
        this.stockData = new ArrayList<>();
        this.profitData = new ArrayList<>();
    }

    /**
     * Constructor with all report details
     * 
     * @param reportId   unique identifier for the report
     * @param branchCode branch code for the report
     * @param reportDate date of the report
     * @param reportType type of report
     * @param salesData  sales data for the report
     * @param stockData  stock data for the report
     * @param profitData profit data for the report
     */
    public Report(String reportId, String branchCode, Date reportDate, String reportType,
            List<Sale> salesData, List<Product> stockData, List<Double> profitData) {
        this.reportId = reportId;
        this.branchCode = branchCode;
        this.reportDate = reportDate != null ? new Date(reportDate.getTime()) : new Date();
        this.reportType = reportType != null ? reportType : DEFAULT_REPORT_TYPE;
        this.salesData = salesData != null ? new ArrayList<>(salesData) : new ArrayList<>();
        this.stockData = stockData != null ? new ArrayList<>(stockData) : new ArrayList<>();
        this.profitData = profitData != null ? new ArrayList<>(profitData) : new ArrayList<>();
    }

    // Business logic methods
    /**
     * Get the total sales amount from sales data
     * 
     * @return total sales amount
     */
    public double getTotalSalesAmount() {
        return salesData.stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();
    }

    /**
     * Get the total number of sales transactions
     * 
     * @return number of sales
     */
    public int getTotalSalesCount() {
        return salesData.size();
    }

    /**
     * Get the total stock value from stock data
     * 
     * @return total stock value
     */
    public double getTotalStockValue() {
        return stockData.stream()
                .mapToDouble(Product::getStockValue)
                .sum();
    }

    /**
     * Get the total number of products in stock
     * 
     * @return number of products
     */
    public int getTotalProductCount() {
        return stockData.size();
    }

    /**
     * Get the total profit from profit data
     * 
     * @return total profit
     */
    public double getTotalProfit() {
        return profitData.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    /**
     * Get the average profit per transaction
     * 
     * @return average profit, or 0 if no sales
     */
    public double getAverageProfit() {
        if (salesData.isEmpty()) {
            return 0.0;
        }
        return getTotalProfit() / salesData.size();
    }

    /**
     * Check if the report has any data
     * 
     * @return true if report has any data, false otherwise
     */
    public boolean hasData() {
        return !salesData.isEmpty() || !stockData.isEmpty() || !profitData.isEmpty();
    }

    /**
     * Get the report summary as a formatted string
     * 
     * @return formatted summary
     */
    public String getSummary() {
        return String.format("Report %s - Branch: %s, Sales: %d, Stock Value: $%.2f, Profit: $%.2f",
                reportId, branchCode, getTotalSalesCount(), getTotalStockValue(), getTotalProfit());
    }

    /**
     * Add a sale to the report
     * 
     * @param sale sale to add
     */
    public void addSale(Sale sale) {
        if (sale != null) {
            salesData.add(sale);
        }
    }

    /**
     * Add a product to the stock data
     * 
     * @param product product to add
     */
    public void addProduct(Product product) {
        if (product != null) {
            stockData.add(product);
        }
    }

    /**
     * Add profit data to the report
     * 
     * @param profit profit amount to add
     */
    public void addProfit(double profit) {
        profitData.add(profit);
    }

    // Getters and Setters
    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Date getReportDate() {
        return reportDate != null ? new Date(reportDate.getTime()) : null;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate != null ? new Date(reportDate.getTime()) : new Date();
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType != null ? reportType : DEFAULT_REPORT_TYPE;
    }

    public List<Sale> getSalesData() {
        return new ArrayList<>(salesData); // Return a copy to prevent external modification
    }

    public void setSalesData(List<Sale> salesData) {
        this.salesData = salesData != null ? new ArrayList<>(salesData) : new ArrayList<>();
    }

    public List<Product> getStockData() {
        return new ArrayList<>(stockData); // Return a copy to prevent external modification
    }

    public void setStockData(List<Product> stockData) {
        this.stockData = stockData != null ? new ArrayList<>(stockData) : new ArrayList<>();
    }

    public List<Double> getProfitData() {
        return new ArrayList<>(profitData); // Return a copy to prevent external modification
    }

    public void setProfitData(List<Double> profitData) {
        this.profitData = profitData != null ? new ArrayList<>(profitData) : new ArrayList<>();
    }

    // Object methods
    @Override
    public String toString() {
        return String.format("Report{id='%s', branch='%s', type='%s', date=%s}",
                reportId, branchCode, reportType, reportDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Report report = (Report) obj;
        return Objects.equals(reportId, report.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId);
    }
}