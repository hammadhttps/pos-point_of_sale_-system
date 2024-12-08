package controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.Vendor;
import model.vendor_product;

import java.net.URL;
import java.util.ResourceBundle;

public class VendorDetailsController implements Initializable {
    @FXML
    private Label vendorNameLabel;

    @FXML
    private Label vendorIdLabel;

    @FXML
    private Label contactInfoLabel;

    @FXML
    private TableView<vendor_product> productsTable;

    @FXML
    private TableColumn<vendor_product, String> productIdCol;

    @FXML
    private TableColumn<vendor_product, String> nameCol;

    @FXML
    private TableColumn<vendor_product, String> categoryCol;

    @FXML
    private TableColumn<vendor_product, Number> originalPriceCol;

    @FXML
    private TableColumn<vendor_product, Number> salePriceCol;

    @FXML
    private TableColumn<vendor_product, Number> quantityCol;

    private Vendor vendor;

    public VendorDetailsController(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set vendor details
        vendorNameLabel.setText("Vendor: " + vendor.getName());
        vendorIdLabel.setText("Vendor ID: " + vendor.getVendorId());
        contactInfoLabel.setText("Contact: " + vendor.getContactInfo());


        productIdCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProductId())
        );

        nameCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName())
        );

        categoryCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCategory())
        );

        originalPriceCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getOriginalPrice())
        );

        salePriceCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getSalePrice())
        );

        quantityCol.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity())
        );

        // Populate products table
        productsTable.getItems().addAll(vendor.getProductList());
    }
}