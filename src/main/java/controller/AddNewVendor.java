package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Vendor;
import services.VendorDAO;

public class AddNewVendor {
    @FXML
    private TextField vendorIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField contactInfoField;

    public  VendorDAO vendorDAO=new VendorDAO();

    @FXML
    private void handleAddVendor() {
        // Validate input fields
        if (validateInputs()) {
            // Create vendor object or save to database
            String vendorId = vendorIdField.getText().trim();
            String name = nameField.getText().trim();
            String contactInfo = contactInfoField.getText().trim();

            // Here you would typically call a service or repository to save the vendor
            saveVendor(vendorId, name, contactInfo);

            // Show success message
            showAlert("Vendor Added", "New vendor has been successfully added to the system.");

            // Close the window
            closeWindow();
        }
    }

    @FXML
    private void handleCancel() {
        // Simply close the window
        closeWindow();
    }

    private boolean validateInputs() {
        if (vendorIdField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Vendor ID cannot be empty.");
            return false;
        }

        if (nameField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Vendor Name cannot be empty.");
            return false;
        }

        return true;
    }

    private void saveVendor(String vendorId, String name, String contactInfo)
    {
        Vendor vendor=new Vendor(vendorId,name,contactInfo);
        vendorDAO.addVendor(vendor);

    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void closeWindow() {
        // Get the stage (window) and close it
        Stage stage = (Stage) vendorIdField.getScene().getWindow();
        stage.close();
    }
}