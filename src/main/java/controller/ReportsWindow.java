package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import model.Sale;
import model.Product; // Assuming the Product class exists
import services.SaleDAO;

import java.text.SimpleDateFormat;
import java.util.*;

public class ReportsWindow {

    private String branchcode;
    private List<Sale> sales = new ArrayList<>();
    private final SaleDAO saleDAO = new SaleDAO();

    @FXML
    private Pane displayPane; // Pane to display graphs/charts

    public void setBranchcode(String bc) {
        this.branchcode = bc;
        sales = saleDAO.getSalesByBranchCode(bc);
    }

    // Display a line chart for sales graph
    public void sales_graph(ActionEvent event) {
        LineChart<String, Number> lineChart = createSalesLineChart();
        displayPane.getChildren().clear();
        displayPane.getChildren().add(lineChart);
    }

    // Display a bar chart for sales chart
    public void sales_chart(ActionEvent event) {
        BarChart<String, Number> barChart = createSalesBarChart();
        displayPane.getChildren().clear();
        displayPane.getChildren().add(barChart);
    }

    // Inventory button placeholder
    public void inventory(ActionEvent event) {
        // Implement inventory details display as needed
    }

    // Create a LineChart to show total sales over time
    private LineChart<String, Number> createSalesLineChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Total Sales Amount");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sales Graph");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Sales");

        // Populate the series with sales data
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        for (Sale sale : sales) {
            series.getData().add(new XYChart.Data<>(dateFormat.format(sale.getDate()), sale.getTotalAmount()));
        }

        lineChart.getData().add(series);
        return lineChart;
    }

    // Create a BarChart to show sales per product
    private BarChart<String, Number> createSalesBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Product");
        yAxis.setLabel("Quantity Sold");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Sales Chart");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Products Sold");

        // Aggregate sales data per product
        Map<String, Integer> productSalesMap = new HashMap<>();
        for (Sale sale : sales) {
            for (Product product : sale.getProducts()) {
                String productId = product.getProductId(); // Or use product.getProductName() if necessary
                int quantitySold = product.getQuantity();

                // Add product sales to the map, aggregating quantities
                productSalesMap.put(productId, productSalesMap.getOrDefault(productId, 0) + quantitySold);
            }
        }

        // Add aggregated product sales data to the series
        for (Map.Entry<String, Integer> entry : productSalesMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
        return barChart;
    }
}
