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
import model.Branch;
import model.Cashier;
import services.BranchDAO;
import services.CashierDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;

public class cashier_login {
    @FXML
    public ImageView _icon;
    
    @FXML
    public TextField username;
    
    @FXML
    public PasswordField password;
    
    @FXML
    public Button loginButton;
    
    int count = 0;
    Branch branch = new Branch();
    BranchDAO branchDAO = new BranchDAO();
    Cashier cashier = new Cashier();
    CashierDAO cashierDAO = new CashierDAO();

    // On login button click
    public void On_login(ActionEvent actionEvent) {
        if (authenticate(username.getText(), password.getText())) {

            _icon.setImage(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/com/example/project_pos/icons_and_images/Lock-Unlock-icon.png"))));
            cashier = cashierDAO.getCashier(username.getText());
            branch = branchDAO.getBranchByCode(cashier.getBranchCode());

            if (branch.isActive()) {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/com/example/project_pos/cashier-modern.fxml"));
                    Parent root = loader.load();

                    CashierModernController csh = loader.getController();
                    csh.get_cashier_detail(username.getText());

                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Show alert message when the branch is not active
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inactive Branch");
                alert.setHeaderText(null);
                alert.setContentText("This branch is not active yet. Please contact your administrator.");
                alert.showAndWait();
            }
        } else {
            // Invalid login
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

    private boolean authenticate(String username, String password) {

        Connection connection = DBConnection.getInstance().getConnection();
        try {

            String sql = "SELECT * FROM Cashier WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // If resultSet has any row, credentials are correct
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
