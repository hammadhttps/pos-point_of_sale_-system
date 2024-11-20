package controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class second_page_controller {

    @FXML
    private Label sup_text, bm_text, csh_text, dt_text;

    @FXML
    private Button cancel_btn;

    @FXML
    private ImageView supadmin_img, BM_img, data_img, cash_img;

    @FXML
    private AnchorPane anchorPane;

    // Continuous fade-in and fade-out for images
    private void fadeInAndOutImages() {
        // Fade-in and fade-out for Super Admin image
        FadeTransition fadeSupAdmin = new FadeTransition(Duration.seconds(2), supadmin_img);
        fadeSupAdmin.setFromValue(0);
        fadeSupAdmin.setToValue(1);
        fadeSupAdmin.setCycleCount(FadeTransition.INDEFINITE);
        fadeSupAdmin.setAutoReverse(true);  // Auto-reverse to fade in and out
        fadeSupAdmin.play();

        // Fade-in and fade-out for Branch Manager image
        FadeTransition fadeBM = new FadeTransition(Duration.seconds(2), BM_img);
        fadeBM.setFromValue(0);
        fadeBM.setToValue(1);
        fadeBM.setCycleCount(FadeTransition.INDEFINITE);
        fadeBM.setAutoReverse(true);
        fadeBM.play();

        // Fade-in and fade-out for Data Entry image
        FadeTransition fadeData = new FadeTransition(Duration.seconds(2), data_img);
        fadeData.setFromValue(0);
        fadeData.setToValue(1);
        fadeData.setCycleCount(FadeTransition.INDEFINITE);
        fadeData.setAutoReverse(true);
        fadeData.play();

        // Fade-in and fade-out for Cashier image
        FadeTransition fadeCash = new FadeTransition(Duration.seconds(2), cash_img);
        fadeCash.setFromValue(0);
        fadeCash.setToValue(1);
        fadeCash.setCycleCount(FadeTransition.INDEFINITE);
        fadeCash.setAutoReverse(true);
        fadeCash.play();
    }

    // Drop transition for the text labels
    private void dropText() {
        TranslateTransition translateSup = new TranslateTransition(Duration.seconds(1), sup_text);
        translateSup.setByY(100); // Move down by 100 units
        translateSup.setDelay(Duration.seconds(0.5)); // Delay for sequential drop
        translateSup.play();

        TranslateTransition translateBM = new TranslateTransition(Duration.seconds(1), bm_text);
        translateBM.setByY(100);
        translateBM.setDelay(Duration.seconds(1)); // Delay for sequential drop
        translateBM.play();

        TranslateTransition translateCsh = new TranslateTransition(Duration.seconds(1), csh_text);
        translateCsh.setByY(100);
        translateCsh.setDelay(Duration.seconds(1.5)); // Delay for sequential drop
        translateCsh.play();

        TranslateTransition translateDt = new TranslateTransition(Duration.seconds(1), dt_text);
        translateDt.setByY(100);
        translateDt.setDelay(Duration.seconds(2)); // Delay for sequential drop
        translateDt.play();
    }

    // Call all animations
    public void initialize() {

        fadeInAndOutImages();
        dropText();
        cancel_btn.setOnAction(this::cance_action);

    }

    public void cance_action(ActionEvent actionEvent) {


        Stage stg=(Stage) cancel_btn.getScene().getWindow();
        stg.close();

    }

    public void csh_action(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/cashier_login.fxml"));
            Parent root = loader.load();

            // Get the current stage and close it
            Stage currentStage = (Stage) cancel_btn.getScene().getWindow();
            currentStage.close();

            // Create a new stage for the cashier login screen
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void data_btn(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/Data_entry_operator_login.fxml"));
            Parent root = loader.load();

            // Get the current stage and close it
            Stage currentStage = (Stage) cancel_btn.getScene().getWindow();
            currentStage.close();

            // Create a new stage for the cashier login screen
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bm_action(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/branch_manager_login.fxml"));
            Parent root = loader.load();

            // Get the current stage and close it
            Stage currentStage = (Stage) cancel_btn.getScene().getWindow();
            currentStage.close();

            // Create a new stage for the cashier login screen
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sa_action(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/super_admin_login.fxml"));
            Parent root = loader.load();

            // Get the current stage and close it
            Stage currentStage = (Stage) cancel_btn.getScene().getWindow();
            currentStage.close();

            // Create a new stage for the cashier login screen
            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
