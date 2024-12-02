package controller;
import DB.DBConnection;
import DB.DBHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class SuperAdmin {
    // ADD THESE FIRST 6 ATTRIBUTES TO DB IN THE FUNCTION ILLUSTRATED BELOW.
    String branch_name;
    String branch_id;
    String branch_address;
    String branch_city;
    int employee_count;
    boolean isActive;
    @FXML
    RadioButton yes,no;
    @FXML
    Button branchButton;
    @FXML
    Button button2;
    Parent root;
    Stage stage;
    Scene scene;
    @FXML
    Button createButton;
    @FXML
    TextField nameLabel;
    @FXML
    TextField idLabel;
    @FXML
    TextField cityLabel;
    @FXML
    TextField addressLabel;
    @FXML
    TextField countLabel;
    @FXML
    TextField nameLabel2;
    @FXML
    TextField emailLabel2;
    @FXML
    TextField passwordLabel2;
    @FXML
    TextField branchidLabel2;
    @FXML
    TextField manageridLabel2;
    @FXML
      Button createButton2;



    public void createBranch(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/project_pos/SuperAdmin_CreateBranch_UI.fxml"));

        stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void createBranchManager(ActionEvent e) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/SuperAdmin_CreateBranchManager_UI.fxml"));
        stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); // No header
        alert.setContentText(message); // The error message
        alert.showAndWait(); // Show the alert and wait for user to close it
    }
    public void go_back(ActionEvent event) throws IOException {

        root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/SuperAdmin_UI.fxml"));
        stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void branchsubmission(ActionEvent event) throws IOException {
        try {
            branch_name = nameLabel.getText();
            branch_id = idLabel.getText();
            branch_city = idLabel.getText();
            branch_address = addressLabel.getText();
            if(branch_name.isEmpty())
            {
                showAlert("Please enter the branch name!");
                return;
            }
            if(branch_city.isEmpty())
            {
                showAlert("Please enter the city name!");
                cityLabel.requestFocus();
                return;
            }
            if(branch_address.isEmpty())
            {
                showAlert("Please enter the branch address!");
                cityLabel.requestFocus();
                return;
            }
            if(branch_id.isEmpty())
            {
                showAlert("Please enter the branch id!");
                idLabel.requestFocus();
                return;
            }
            String county=countLabel.getText();
            if(county.isEmpty())
            {
                showAlert("Please enter employee count!");
                countLabel.requestFocus();
                return;
            }
            try {
                employee_count = Integer.parseInt(countLabel.getText());
                if(employee_count<0)
                {
                    showAlert("Count cannot be negative!");
                    return;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid employee count.");
                throw new RuntimeException(e);
            }
            if(yes.isSelected())
            {
                isActive=true;
            }
            else if(no.isSelected())
            {
                isActive=false;
            }
            else {

                showAlert("Please state the branch status!");
                return;
            }
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Branch Creation");
            alert.setHeaderText("You're about to create a branch!");
            alert.setContentText("Do you want to continue?");
            if(alert.showAndWait().get()== ButtonType.OK)
            {
                Connection connection = DBConnection.getInstance().getConnection();
                DBHelper setter=new DBHelper(connection);
                setter.InsertBranch(branch_id,branch_name,branch_address,branch_city,isActive,employee_count);
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setHeaderText("Branch added succesfully.");
                alert1.show();
                root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/SuperAdmin_UI.fxml"));
                stage =(Stage)((Node)event.getSource()).getScene().getWindow();
                scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void branchManagersubmission(ActionEvent e) throws IOException {
        String name = nameLabel2.getText();
        String email = emailLabel2.getText();
        String password = passwordLabel2.getText();
        String branchId = branchidLabel2.getText();
        String managerId = manageridLabel2.getText();
        if (name.isEmpty()) {
            showAlert("Please enter name of branch manager!");
            return;
        } else if (email.isEmpty()) {
            showAlert("Please enter email of branch manager!");
            return;
        } else if (password.isEmpty()) {
            showAlert("Please enter password of branch manager!");
            return;
        } else if (branchId.isEmpty()) {
            showAlert("Please enter branch id!");
            return;
        } else if (managerId.isEmpty()) {
            showAlert("Please enter id of branch manager!");
            return;
        }
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setHeaderText("Would you like to continue ? ");
        if (alert1.showAndWait().get() == ButtonType.OK)
        {
          Connection connection=DBConnection.getInstance().getConnection();
          DBHelper helper=new DBHelper(connection);
          helper.InsertBranchManager(name,email,password,branchId,managerId);
            Alert alert3=new Alert(Alert.AlertType.INFORMATION);
            alert3.setHeaderText("Branch Manager Added Succesfully");
            alert3.show();
            root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/SuperAdmin_UI.fxml"));
            stage =(Stage)((Node)e.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    public  void branch_back(ActionEvent e) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/SuperAdmin_UI.fxml"));
        stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
