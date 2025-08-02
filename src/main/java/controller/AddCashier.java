package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Cashier;
import services.CashierDAO;

public class AddCashier {

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

    private  static String branchcode;

    private CashierDAO cashierDAO = new CashierDAO();


    public static void setBranchcode(String branchcode1) {
       branchcode = branchcode1;
    }


    public void handleAddCashier(ActionEvent event) {
        String username = usernameField.getText();
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String salary = salaryField.getText();
        String role = "Cashier";
        String bc = branchcode;
        if(salary.isEmpty()||username.isEmpty()||password.isEmpty())
        {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add cashier. Please try again.");
        }

        Cashier cashier = new Cashier();
        cashier.setUsername(username);
        cashier.setName(name);
        cashier.setEmail(email);
        cashier.setRole(role);
        cashier.setBranchCode(bc);
        cashier.setSalary(Double.parseDouble(salary));
        cashier.setPassword(password);

        if (cashierDAO.addCashier(cashier)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Cashier added successfully!");


            usernameField.clear();
            nameField.clear();
            emailField.clear();
            passwordField.clear();
            salaryField.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add cashier. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
