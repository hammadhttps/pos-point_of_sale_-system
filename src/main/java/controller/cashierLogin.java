package controller;


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
import java.util.Objects;

public class cashierLogin {


    public ImageView _icon;
    public TextField username;
    public PasswordField password;
    public Button loginButton;
    int count=0;

    // Simulated authentication check
    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "1234".equals(password); // Replace with your authentication logic
    }

    public void  On_login(ActionEvent actionEvent) {
        if (authenticate(username.getText(), password.getText())) {

            // Successful login: Replace the lock icon with the unlock icon
            _icon.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/project_pos/icons_and_images/Lock-Unlock-icon.png"))));
        } else {
            // Invalid login: Vibrate the login button
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
            // Load the second screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/second_screen.fxml"));
            Parent root = loader.load();
            //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("src/main/resources/com/example/project_pos/second_screen.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
