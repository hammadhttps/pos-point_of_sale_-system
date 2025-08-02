package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.Vendor;
import model.VendorProduct;

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
        private TableView<VendorProduct> productsTable;

        @FXML
        private TableColumn<VendorProduct, String> productIdCol;

        @FXML
        private TableColumn<VendorProduct, String> nameCol;

        @FXML
        private TableColumn<VendorProduct, String> categoryCol;

        @FXML
        private TableColumn<VendorProduct, Number> originalPriceCol;

        @FXML
        private TableColumn<VendorProduct, Number> salePriceCol;

        @FXML
        private TableColumn<VendorProduct, Number> quantityCol;

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

                productIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                                cellData.getValue().getProductId()));

                nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                                cellData.getValue().getName()));

                categoryCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                                cellData.getValue().getCategory()));

                originalPriceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
                                cellData.getValue().getOriginalPrice()));

                salePriceCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(
                                cellData.getValue().getSalePrice()));

                quantityCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(
                                cellData.getValue().getQuantity()));

                // Populate products table
                productsTable.getItems().addAll(vendor.getProductList());
        }
}