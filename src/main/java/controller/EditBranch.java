package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Branch;
import model.BranchManager;
import model.Cashier;
import model.DataEntryOperator;
import services.BranchDAO;
import services.BranchManagerDAO;
import services.CashierDAO;
import services.DataEntryOperatorDAO;

import java.util.List;

public class EditBranch {

    @FXML
    private ListView<Branch> activeBranchList;
    @FXML
    private ListView<Branch> inactiveBranchList;
    @FXML
    private ListView<String> cashiersList;
    @FXML
    private ListView<String> dataEntryOperatorsList;
    @FXML
    private Label branchManagerLabel;

    @FXML
    private Button activateButton, deactivateButton, increaseEmpCountButton;

    private final BranchDAO branchDAO = new BranchDAO();
    private final CashierDAO cashierDAO = new CashierDAO();
    private final DataEntryOperatorDAO dataEntryOperatorDAO = new DataEntryOperatorDAO();
    private final BranchManagerDAO branchManagerDAO = new BranchManagerDAO();

    private final ObservableList<Branch> activeBranches = FXCollections.observableArrayList();
    private final ObservableList<Branch> inactiveBranches = FXCollections.observableArrayList();

    public void initialize() {
        loadBranches();


        activeBranchList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Branch branch, boolean empty) {
                super.updateItem(branch, empty);
                setText(empty || branch == null ? null : branch.getName());
            }
        });

        inactiveBranchList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Branch branch, boolean empty) {
                super.updateItem(branch, empty);
                setText(empty || branch == null ? null : branch.getName());
            }
        });

        // Event handlers
        activateButton.setOnAction(event -> activateBranch());
        deactivateButton.setOnAction(event -> deactivateBranch());
        increaseEmpCountButton.setOnAction(event -> increaseEmployeeCount());

        activeBranchList.setOnMouseClicked(event -> showBranchDetails(activeBranchList.getSelectionModel().getSelectedItem()));
        inactiveBranchList.setOnMouseClicked(event -> showBranchDetails(inactiveBranchList.getSelectionModel().getSelectedItem()));
    }

    private void loadBranches() {
        List<Branch> branches = branchDAO.getAllBranches();

        // Separate active and inactive branches
        activeBranches.clear();
        inactiveBranches.clear();
        for (Branch branch : branches) {
            if (branch.isActive()) {
                activeBranches.add(branch);
            } else {
                inactiveBranches.add(branch);
            }
        }

        activeBranchList.setItems(activeBranches);
        inactiveBranchList.setItems(inactiveBranches);
    }

    private void activateBranch() {
        Branch selectedBranch = inactiveBranchList.getSelectionModel().getSelectedItem();
        if (selectedBranch != null) {
            selectedBranch.setActive(true);
            branchDAO.updateBranch(selectedBranch);
            loadBranches();
            showAlert("Success", "Branch activated successfully.");
        } else {
            showAlert("Error", "Please select a branch to activate.");
        }
    }

    private void deactivateBranch() {
        Branch selectedBranch = activeBranchList.getSelectionModel().getSelectedItem();
        if (selectedBranch != null) {
            selectedBranch.setActive(false);
            branchDAO.updateBranch(selectedBranch);
            loadBranches();
            showAlert("Success", "Branch deactivated successfully.");
        } else {
            showAlert("Error", "Please select a branch to deactivate.");
        }
    }

    private void increaseEmployeeCount() {
        Branch selectedBranch = activeBranchList.getSelectionModel().getSelectedItem();
        if (selectedBranch != null) {
            selectedBranch.setEmployeeCount(selectedBranch.getEmployeeCount() + 1);
            branchDAO.updateBranch(selectedBranch);
            loadBranches();
            showAlert("Success", "Employee count increased for branch.");
        } else {
            showAlert("Error", "Please select a branch to increase employee count.");
        }
    }

    private void showBranchDetails(Branch branch) {
        if (branch != null) {
            // Fetch branch manager for the branch
            BranchManager manager = branchManagerDAO.getBranchManagerByBranchCode(branch.getBranchcode());

            if (manager != null) {
                branchManagerLabel.setText("Branch Manager: " + manager.getName() + " (" + manager.getEmail() + ")");
            } else {
                branchManagerLabel.setText("Branch Manager: Not Assigned");
            }

            // Fetch cashiers and data entry operators for the branch
            List<Cashier> cashiers = cashierDAO.getAllCashiersByBranchCode(branch.getBranchcode());
            List<DataEntryOperator> dataEntryOperators = dataEntryOperatorDAO.getDataEntryOperatorsByBranchCode(branch.getBranchcode());

            // Update list views
            cashiersList.setItems(FXCollections.observableArrayList(
                    cashiers.stream().map(Cashier::getName).toList()
            ));
            dataEntryOperatorsList.setItems(FXCollections.observableArrayList(
                    dataEntryOperators.stream().map(DataEntryOperator::getName).toList()
            ));
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
