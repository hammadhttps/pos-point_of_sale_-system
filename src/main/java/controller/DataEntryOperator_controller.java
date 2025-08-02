package controller;

import DB.DBConnection;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.util.Duration;
import model.DataEntryOperator;
import services.DataEntryOperatorDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javafx.fxml.FXML;

public class DataEntryOperator_controller {


    @FXML
    public ImageView _icon;
    
    @FXML
    public TextField username;
    
    @FXML
    public PasswordField password;
    
    @FXML
    public Button loginButton;
    
    @FXML
    public AnchorPane mainAnchorPane;
    
    @FXML
    public AnchorPane innerAnchorPane;
    
    @FXML
    public Label welcome_label;
    
    @FXML
    public ImageView vendorsIcon;
    
    @FXML
    public ImageView addVendorIcon;
    
    @FXML
    public ImageView addProductIcon;
    int count = 0;


    private boolean authenticate(String username, String password) {
        Connection connection = DBConnection.getInstance().getConnection();

        try {

            String sql = "SELECT * FROM DataEntryOperator WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Authentication failed
    }

    // On login button click
    public void On_login(ActionEvent actionEvent) throws IOException {
        if (authenticate(username.getText(), password.getText())) {

            _icon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/project_pos/icons_and_images/Lock-Unlock-icon.png"))));
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/com/example/project_pos/data_entry_operator.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } else {

            shakeButton(loginButton);
            loginButton.setText("Invalid");
        }
        loginButton.setText("Login");
    }


    private void shakeButton(Button button) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(100), button);
        transition.setByX(10);
        transition.setCycleCount(4);
        transition.setAutoReverse(true);
        transition.play();
    }


    public void home_pressed(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/second_screen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void cancel(ActionEvent event) {
        Platform.exit();
    }

    // (Switch to Home screen)
    public void goToHomeScreen(MouseEvent event) throws IOException {
        // Load the second screen FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/second_screen.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void add_new_vendor(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/add_new_vendor.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add New Product");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }

    public void add_new_product(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/add_product.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add New Product");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }

    public void show_vendors(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/vendors.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add New Product");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();


    }
}
