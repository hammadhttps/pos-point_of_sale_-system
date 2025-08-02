package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.DataEntryOperator;
import services.DataEntryOperatorDAO;

public class AddDataEntryOperator {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField salaryField;

    private static String bc1;

    private DataEntryOperatorDAO dataEntryOperatorDAO = new DataEntryOperatorDAO();

    public static void setBranchcode(String bc) {
        bc1 = bc;
    }

    public void handleAddDataEntry(ActionEvent event) {
        String username = usernameField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String salary = salaryField.getText();
        String role = "Data Entry Operator";
        String branchcode = bc1;

        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || salary.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }

        DataEntryOperator dataEntryOperator = new DataEntryOperator();
        dataEntryOperator.setName(name);
        dataEntryOperator.setUsername(username);
        dataEntryOperator.setRole(role);
        dataEntryOperator.setEmail(email);
        dataEntryOperator.setSalary(Double.parseDouble(salary));
        dataEntryOperator.setPassword(password);
        dataEntryOperator.setBranchCode(branchcode);

        if (dataEntryOperatorDAO.addDataEntryOperator(dataEntryOperator)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Operator added successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add operator. Try again.");
        }

        // Clear the fields after processing
        usernameField.clear();
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        salaryField.clear();
    }

    // Utility method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}