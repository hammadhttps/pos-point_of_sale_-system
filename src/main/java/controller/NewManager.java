package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Branch;
import model.BranchManager;
import services.BranchDAO;
import services.BranchManagerDAO;

public class NewManager {

    private final BranchManagerDAO branchManagerDAO = new BranchManagerDAO();
    private final BranchManager branchManager = new BranchManager();
    private final BranchDAO branchDAO = new BranchDAO();

    public Button addManagerButton;
    public TextField salaryField;
    public TextField branchCodeField;
    public PasswordField passwordField;
    public TextField nameField;
    public TextField usernameField;
    public TextField emailField;

    public void addManagerAction(ActionEvent event) {


        if (isAnyFieldEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields before proceeding.");
            alert.showAndWait();
            return;
        }


        branchManager.setBranchCode(branchCodeField.getText());
        branchManager.setEmail(emailField.getText());
        branchManager.setName(nameField.getText());
        branchManager.setusername(usernameField.getText());
        branchManager.setPassword(passwordField.getText());
        branchManager.setRole("Manager");
        branchManager.setSalary(Double.parseDouble(salaryField.getText()));


        Branch branch = branchDAO.getBranchByCode(branchCodeField.getText());

        if (branch == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Branch Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The branch with the given code does not exist. Please create the branch first.");
            alert.showAndWait();
            return;
        }


        branchManager.setEmployeeCount(branch.getEmployeeCount());


        boolean success = branchManagerDAO.addBranchManager(branchManager);

        if (success) {
            // Display a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Branch Manager has been added successfully!");
            alert.showAndWait();

            clearFields();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while adding the Branch Manager. Please try again.");
            alert.showAndWait();
        }
    }
    private void clearFields() {
        usernameField.clear();
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        branchCodeField.clear();
        salaryField.clear();
    }

    private boolean isAnyFieldEmpty() {
        return usernameField.getText().isEmpty()
                || nameField.getText().isEmpty()
                || emailField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || branchCodeField.getText().isEmpty()
                || salaryField.getText().isEmpty();
    }
}
