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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DataEntryLogin {

    public ImageView _icon;
    public TextField username;
    public PasswordField password;
    public Button loginButton;
    int count = 0;

    // Method to authenticate using data from the database
    private boolean authenticate(String username, String password) {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            // Prepare the SQL query to fetch the username and password from the database
            String sql = "SELECT * FROM DataEntryOperator WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);  // Set the username parameter
            statement.setString(2, password);  // Set the password parameter (In real-world, passwords should be hashed)

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // If a record is returned, authentication is successful
            if (resultSet.next()) {
                return true;  // Successful login
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Authentication failed
    }

    // On login button click, authenticate the user
    public void On_login(ActionEvent actionEvent) {
        if (authenticate(username.getText(), password.getText())) {
            // Successful login: Replace the lock icon with the unlock icon
            _icon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/project_pos/icons_and_images/Lock-Unlock-icon.png"))));
            System.out.println("Login successful!");  // You can replace this with a redirection to a new screen
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
}
