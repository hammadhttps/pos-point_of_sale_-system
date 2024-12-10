package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import services.SaleDAO;
import model.Sale;
import model.Product;
import services.ProductDAO;

import java.text.SimpleDateFormat;
import java.util.*;

public class BranchReports {

    @FXML
    private TextField branchCodeField;

    @FXML
    private Pane displayPane;


    private SaleDAO saleDAO = new SaleDAO();
    private ProductDAO productDAO = new ProductDAO();

    private String branchCode;


    private void fetchBranchData() {
        branchCode = branchCodeField.getText().trim();
    }


    @FXML
    private void generateSalesGraph(ActionEvent event) {
        fetchBranchData();
        if (!branchCode.isEmpty()) {

            List<Sale> sales = saleDAO.getSalesByBranchCode(branchCode);

            displayPane.getChildren().clear();
            generateSalesGraph(sales);
        } else {
            showErrorMessage("Please enter a valid branch code.");
        }
    }

    //  generate Sales Bar Chart
    @FXML
    private void generateSalesBarChart(ActionEvent event) {
        fetchBranchData();
        if (!branchCode.isEmpty()) {
            // Fetch sales data for the branch code
            List<Sale> sales = saleDAO.getSalesByBranchCode(branchCode);
            // Clear previous content
            displayPane.getChildren().clear();
            generateSalesBarChart(sales);
        } else {
            showErrorMessage("Please enter a valid branch code.");
        }
    }

    // generate Product Inventory Table
    @FXML
    private void generateInventoryTable(ActionEvent event) {
        fetchBranchData();
        if (!branchCode.isEmpty()) {

            List<Product> products = productDAO.getAllProducts();

            displayPane.getChildren().clear();
            generateInventoryTable(products);
        } else {
            showErrorMessage("Please enter a valid branch code.");
        }
    }

    //  generate a Sales Line Graph
    private void generateSalesGraph(List<Sale> sales) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Total Sales Amount");

        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Sales Graph");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Total Sales");


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        for (Sale sale : sales) {
            series.getData().add(new XYChart.Data<>(dateFormat.format(sale.getDate()), sale.getTotalAmount()));
        }


        lineChart.getData().add(series);
        displayPane.getChildren().add(lineChart);
    }

    // a Sales Bar Chart
    private void generateSalesBarChart(List<Sale> sales) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Product");
        yAxis.setLabel("Quantity Sold");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Sales Chart");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Products Sold");


        Map<String, Integer> productSalesMap = new HashMap<>();
        for (Sale sale : sales) {
            for (Product product : sale.getProducts()) {
                String productId = product.getProductId();
                int quantitySold = product.getQuantity();
                productSalesMap.put(productId, productSalesMap.getOrDefault(productId, 0) + quantitySold);
            }
        }


        for (Map.Entry<String, Integer> entry : productSalesMap.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }


        barChart.getData().add(series);
        displayPane.getChildren().add(barChart);
    }

    private void generateInventoryTable(List<Product> products) {
        TableView<Product> productTable = new TableView<>();
        TableColumn<Product, String> productIdColumn = new TableColumn<>("Product ID");
        TableColumn<Product, String> productNameColumn = new TableColumn<>("Product Name");
        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");


        productTable.setPrefHeight(400);
        productTable.setPrefWidth(1120);


        productIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProductId()));
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());


        productTable.getColumns().addAll(productIdColumn, productNameColumn, quantityColumn);


        productTable.setItems(javafx.collections.FXCollections.observableArrayList(products));


        displayPane.getChildren().add(productTable);
    }


    private void showErrorMessage(String message) {
        Label errorMessage = new Label(message);
        errorMessage.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        displayPane.getChildren().add(errorMessage);
    }
}
