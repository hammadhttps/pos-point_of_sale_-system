package controller;

import DB.DBConnection;
import DB.DBHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class BranchManager {
    @FXML
    TextField newBranchLabel;
    @FXML
    TextField branchidLabel1;
    @FXML
    Button changeNameButton;
    Parent root;
    Stage stage;
    Scene scene;
    @FXML
    TextField empIdLabel;
    @FXML
    TextField newPasswordLabel;
   @FXML
   TextField branchLabel;
   @FXML
   TextField countLabel;

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null); // No header
        alert.setContentText(message); // The error message
        alert.showAndWait(); // Show the alert and wait for user to close it
    }
     public void changeEmployeeCount(ActionEvent e) throws SQLException {
     String id=branchLabel.getText();
     if(id.isEmpty())
     {
         showAlert("Please enter a branch id.");
         return;
     }
     try
     {
         String number=countLabel.getText();
         if(number.isEmpty())
         {
             showAlert("Please enter an employee count.");
             return;
         }
     }
     catch(NumberFormatException ev)
     {
         showAlert("Please enter a number in the employee count. ");
         ev.printStackTrace();
     }
     int count= Integer.parseInt(countLabel.getText());
     if(count<0)
     {
         showAlert("Employee count cannot be negative.");
         return;
     }
     Alert papi=new Alert(Alert.AlertType.CONFIRMATION);
     if(papi.showAndWait().get()==ButtonType.OK)
     {
         Connection connection=DBConnection.getInstance().getConnection();
         DBHelper helper=new DBHelper(connection);
         helper.employeeCount(id,count);
     }

    }

    public void go_back(ActionEvent e) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/BranchManager_EditCredentials.fxml"));
        stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void go_forward(ActionEvent ep) {
        try {
            // Correct resource path
            root = FXMLLoader.load(getClass().getResource("/com/example/project_pos/BranchManager_EditCredentials.fxml"));

            // Get the current stage and scene
            stage = (Stage) ((Node) ep.getSource()).getScene().getWindow();
            scene = new Scene(root);

            // Set the new scene and show the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Print the error stack trace for better debugging
            e.printStackTrace();
            showAlert("Error loading the FXML file. Please check the file path and ensure it exists.");
        }
    }


    public void go_back2(ActionEvent e) throws IOException {
        root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/BranchManager_UI.fxml"));
        stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
public void changeBranchName(ActionEvent e) throws IOException {
   String id=branchidLabel1.getText();
   String new_name =newBranchLabel.getText();
   if(id.isEmpty())
   {
       showAlert("Please enter the branch id");
       return;
   }
   else if(new_name.isEmpty())
   {
       showAlert("Please enter the new name");
       return;
   }
   Alert alerty =new Alert(Alert.AlertType.CONFIRMATION);
   alerty.setHeaderText("Do you want to continue ?");
   if(alerty.showAndWait().get()== ButtonType.OK)
   {
       Connection connection= DBConnection.getInstance().getConnection();
       DBHelper helper=new DBHelper(connection);
       helper.changeBranchName(id,new_name);
       root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/BranchManager_EditCredentials.fxml"));
       stage =(Stage)((Node)e.getSource()).getScene().getWindow();
       scene=new Scene(root);
       stage.setScene(scene);
       stage.show();
   }

   }
   public void newPassword(ActionEvent e) throws IOException {
    String id=empIdLabel.getText();
    String password=newPasswordLabel.getText();
    if(id.isEmpty())
    {
        showAlert("Please enter the employee id.");
        return;
    }
    else if(password.isEmpty())
    {
        showAlert("Please enter the new password.");
        return;
    }
    Alert alerty=new Alert(Alert.AlertType.CONFIRMATION);
    alerty.setHeaderText("Are you sure you want to continue ?");
    if(alerty.showAndWait().get()==ButtonType.OK)
    {
        Connection connection =DBConnection.getInstance().getConnection();
        DBHelper helper=new DBHelper(connection);
        helper.changePassword(id,password);
        root= FXMLLoader.load(getClass().getResource("/com/example/project_pos/BranchManager_EditCredentials.fxml"));
        stage =(Stage)((Node)e.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   }

}

