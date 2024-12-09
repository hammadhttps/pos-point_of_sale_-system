package controller;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.FXML;
import model.Cashier;
import model.DataEntryOperator;
import services.CashierDAO;
import services.DataEntryOperatorDAO;

import java.util.ArrayList;
import java.util.List;

public class ManagePasswords {

    private String branchCode;
    private List<Cashier> cashiers = new ArrayList<>();
    private List<DataEntryOperator> dataEntryOperators = new ArrayList<>();
    private final CashierDAO cashierDAO = new CashierDAO();
    private final DataEntryOperatorDAO dataEntryOperatorDAO = new DataEntryOperatorDAO();

    @FXML
    private VBox cashierBox;

    @FXML
    private VBox operatorBox;

    @FXML
    private StackPane mainPane;

    @FXML
    private AnchorPane editPane;

    @FXML
    private Label editTitle;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField salaryField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button saveButton;

    private Cashier selectedCashier;
    private DataEntryOperator selectedOperator;

    public void setBranchcode(String bc) {
        branchCode = bc;
        cashiers = cashierDAO.getAllCashiersByBranchCode(branchCode);
        dataEntryOperators = dataEntryOperatorDAO.getDataEntryOperatorsByBranchCode(branchCode);

        displayCashiers();
        displayOperators();
    }

    private void displayCashiers() {
        cashierBox.getChildren().clear();
        for (Cashier cashier : cashiers) {
            VBox cashierContainer = new VBox(5);
            cashierContainer.setPadding(new Insets(10));
            cashierContainer.setStyle("-fx-border-color: #000; -fx-border-radius: 5; -fx-background-color: #cce5ff; -fx-padding: 10;");

            Label username = new Label("Username: " + cashier.getusername());
            Label name = new Label("Name: " + cashier.getName());
            Label email = new Label("Email: " + cashier.getEmail());
            Label salary = new Label("Salary: " + cashier.getSalary());
            Label password = new Label("Password: " + cashier.getPassword());

            Button editButton = new Button("Edit Details");
            editButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 5 10;");

            editButton.setOnAction(event -> showEditPane(cashier, null));

            cashierContainer.getChildren().addAll(username, name, email, salary, password, editButton);
            cashierBox.getChildren().add(cashierContainer);
        }
    }

    private void displayOperators() {
        operatorBox.getChildren().clear();
        for (DataEntryOperator operator : dataEntryOperators) {
            VBox operatorContainer = new VBox(5);
            operatorContainer.setPadding(new Insets(10));
            operatorContainer.setStyle("-fx-border-color: #000; -fx-border-radius: 5; -fx-background-color: #d4edda; -fx-padding: 10;");

            Label username = new Label("Username: " + operator.getusername());
            Label name = new Label("Name: " + operator.getName());
            Label email = new Label("Email: " + operator.getEmail());
            Label salary = new Label("Salary: " + operator.getSalary());
            Label password = new Label("Password: " + operator.getPassword());

            Button editButton = new Button("Edit Details");
            editButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-padding: 5 10;");

            editButton.setOnAction(event -> showEditPane(null, operator));

            operatorContainer.getChildren().addAll(username, name, email, salary, password, editButton);
            operatorBox.getChildren().add(operatorContainer);
        }
    }

    private void showEditPane(Cashier cashier, DataEntryOperator operator) {
        if (cashier != null) {
            selectedCashier = cashier;
            selectedOperator = null;

            editTitle.setText("Edit Cashier Details");
            usernameField.setText(cashier.getusername());
            nameField.setText(cashier.getName());
            emailField.setText(cashier.getEmail());
            salaryField.setText(String.valueOf(cashier.getSalary()));
            passwordField.setText(cashier.getPassword());
        } else if (operator != null) {
            selectedCashier = null;
            selectedOperator = operator;

            editTitle.setText("Edit Operator Details");
            usernameField.setText(operator.getusername());
            nameField.setText(operator.getName());
            emailField.setText(operator.getEmail());
            salaryField.setText(String.valueOf(operator.getSalary()));
            passwordField.setText(operator.getPassword());
        }
        editPane.setVisible(true);
    }

    @FXML
    private void saveDetails() {
        if (selectedCashier != null) {
            selectedCashier.setusername(usernameField.getText());
            selectedCashier.setName(nameField.getText());
            selectedCashier.setEmail(emailField.getText());
            selectedCashier.setSalary(Double.parseDouble(salaryField.getText()));
            selectedCashier.setPassword(passwordField.getText());
            cashierDAO.updateCashier(selectedCashier);
        } else if (selectedOperator != null) {
            selectedOperator.setusername(usernameField.getText());
            selectedOperator.setName(nameField.getText());
            selectedOperator.setEmail(emailField.getText());
            selectedOperator.setSalary(Double.parseDouble(salaryField.getText()));
            selectedOperator.setPassword(passwordField.getText());
            dataEntryOperatorDAO.updateDataEntryOperator(selectedOperator);
        }
        editPane.setVisible(false);
        setBranchcode(branchCode);
    }

    @FXML
    private void closeEditPane() {
        editPane.setVisible(false);
    }
}
