package controller;

import DB.DBConnection;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BranchManager;
import services.BranchManagerDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class BranchManager_controller {


    private final BranchManagerDAO branchManagerDAO=new BranchManagerDAO();
    private BranchManager branchManager=null;
    public ImageView _icon;
    public TextField username;
    public PasswordField password;
    public Button loginButton;
    int count = 0;
    public static String uname;
    public  static String bc;


    // Method to authenticate using data from the database
    private boolean authenticate(String username, String password) {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            // Prepare the SQL query to fetch the username and password from the database
            String sql = "SELECT * FROM branchmanager WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // If a record is returned, authentication is successful
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Authentication failed
    }

    // On login button click, authenticate the user
    public void On_login(ActionEvent actionEvent) throws IOException {

        if (authenticate(username.getText(), password.getText())) {
            // Successful login: Replace the lock icon with the unlock icon
            uname=username.getText();
            _icon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/project_pos/icons_and_images/Lock-Unlock-icon.png"))));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/Branch_manager.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // Invalid login: Vibrate the login button
            shakeButton(loginButton);
            loginButton.setText("Invalid");
        }
        loginButton.setText("Login");
    }

    // Method to shake the login button if the login is invalid
    private void shakeButton(Button button) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), button);
        transition.setByX(10);
        transition.setCycleCount(4);
        transition.setAutoReverse(true);
        transition.play();
    }

    // On home button press, navigate to another screen (second screen)
    public void home_pressed(ActionEvent actionEvent) {
        try {
            // Load the second screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/second_screen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void manage_passwords(MouseEvent mouseEvent) {

    }

    public void exit(MouseEvent mouseEvent) {

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void generate_reports(MouseEvent mouseEvent)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/reports_window.fxml"));
            Parent root = loader.load();
            ReportsWindow controller = loader.getController();// Retrieve the controller
            branchManager=branchManagerDAO.getBranchManager(uname);
            controller.setBranchcode(branchManager.getBranchCode());

            Stage stage = new Stage();
            stage.setTitle("Report Window");
            stage.setScene(new Scene(root));
            stage.show(); // Display the new stage
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void add_data_entry_operator(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/Add_data_entry_operator.fxml"));
        Parent root = loader.load();
        AddDataEntryOperator controller = loader.getController();
        branchManager=branchManagerDAO.getBranchManager(uname);
        bc=branchManager.getBranchCode();
        AddDataEntryOperator.setBranchcode(bc);
        Stage stage = new Stage();
        stage.setTitle("Add Data Entry Operator");
        stage.setScene(new Scene(root));
        stage.show();


    }

    public void add_cashier(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/Add_cashier.fxml"));
        Parent root = loader.load();
        AddCashier controller = loader.getController();
        branchManager=branchManagerDAO.getBranchManager(uname);
        bc=branchManager.getBranchCode();
        AddCashier.setBranchcode(bc);
        Stage stage = new Stage();
        stage.setTitle("Add Cashier");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
