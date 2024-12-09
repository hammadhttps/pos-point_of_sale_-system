package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.Branch;
import services.BranchDAO;

public class NewBranch {

    @FXML private TextField nameField;
    @FXML private TextField cityField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private CheckBox isActiveField;
    @FXML private TextField employeeCountField;
    @FXML private TextField branchCodeField;

    private BranchDAO branchDAO = new BranchDAO();
    private Branch branch = new Branch();

    @FXML
    public void addBranchAction(ActionEvent event) {

        if (nameField.getText().isEmpty() || cityField.getText().isEmpty() || addressField.getText().isEmpty() || phoneField.getText().isEmpty() || branchCodeField.getText().isEmpty() || employeeCountField.getText().isEmpty()) {
            showAlert(AlertType.WARNING, "Form Incomplete", "Please fill in all fields.");
            return;
        }
        String name = nameField.getText();
        String city = cityField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        boolean isActive = isActiveField.isSelected();
        int employeeCount = Integer.parseInt(employeeCountField.getText());
        String branchCode = branchCodeField.getText();

        // Validate the fields before adding


        // Set values to branch object
        branch.setName(name);
        branch.setCity(city);
        branch.setAddress(address);
        branch.setPhone(phone);
        branch.setActive(isActive);
        branch.setEmployeeCount(employeeCount);
        branch.setBranchcode(branchCode);

        // Try to add the branch
        if (branchDAO.addBranch(branch)) {
            showAlert(AlertType.INFORMATION, "Branch Added", "The new branch has been successfully added.");
        } else {
            showAlert(AlertType.ERROR, "Error", "Failed to add the branch. Please try again.");
        }
        nameField.clear();
        employeeCountField.clear();
        phoneField.clear();
        branchCodeField.clear();
        addressField.clear();
        cityField.clear();
    }



    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
