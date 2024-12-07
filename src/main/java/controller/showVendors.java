package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Vendor;
import model.vendor_product;
import services.VendorDAO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class showVendors implements Initializable {
    @FXML
    private VBox vendorsContainer;

    private VendorDAO vendorDAO;

    public showVendors() {
        this.vendorDAO = new VendorDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadVendors();
    }

    private void loadVendors() {
        List<Vendor> vendors = vendorDAO.getAllVendors();

        for (Vendor vendor : vendors) {
            VBox vendorBox = createVendorBox(vendor);
            vendorsContainer.getChildren().add(vendorBox);
        }
    }

    private VBox createVendorBox(Vendor vendor) {
        // Vendor Details Box
        VBox vendorDetailsBox = new VBox(10);
        vendorDetailsBox.getStyleClass().add("vendor-box");

        // Vendor Information Labels
        Label vendorNameLabel = new Label("Vendor: " + vendor.getName());
        vendorNameLabel.getStyleClass().add("vendor-name");

        Label vendorIdLabel = new Label("Vendor ID: " + vendor.getVendorId());
        vendorIdLabel.getStyleClass().add("vendor-id");

        Label contactInfoLabel = new Label("Contact: " + vendor.getContactInfo());
        contactInfoLabel.getStyleClass().add("vendor-contact");

        // View Details Button
        Button viewDetailsButton = new Button("View Vendor Details");
        viewDetailsButton.setOnAction(event -> {
            openVendorDetailsPage(vendor);
        });

        // Add all elements to vendor details box
        vendorDetailsBox.getChildren().addAll(
                vendorNameLabel,
                vendorIdLabel,
                contactInfoLabel,
                viewDetailsButton
        );

        return vendorDetailsBox;
    }

    private void openVendorDetailsPage(Vendor vendor) {
        try {
            // Load the vendor details FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/vendor_details.fxml"));

            // Create the controller and set the vendor
            VendorDetailsController detailsController = new VendorDetailsController(vendor);
            loader.setController(detailsController);

            // Load the parent
            Parent root = loader.load();

            // Create a new stage
            Stage vendorDetailsStage = new Stage();
            vendorDetailsStage.setTitle("Vendor Details - " + vendor.getName());
            vendorDetailsStage.setScene(new Scene(root));

            // Show the stage
            vendorDetailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally show an error dialog
            Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load vendor details page", ButtonType.OK);
            alert.show();
        }
    }
}