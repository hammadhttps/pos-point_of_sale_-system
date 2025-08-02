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
    private Button sa_btn;

    @FXML
    private Button bm_btn;

    @FXML
    private Button csh_btn;

    @FXML
    private Button data_btn;

    @FXML
    private ImageView supadmin_img, BM_img, data_img, cash_img;

    @FXML
    private AnchorPane anchorPane;

    private void fadeInAndOutImages() {

        FadeTransition fadeSupAdmin = new FadeTransition(Duration.seconds(2), supadmin_img);
        fadeSupAdmin.setFromValue(0);
        fadeSupAdmin.setToValue(1);
        fadeSupAdmin.setCycleCount(FadeTransition.INDEFINITE);
        fadeSupAdmin.setAutoReverse(true);
        fadeSupAdmin.play();

        FadeTransition fadeBM = new FadeTransition(Duration.seconds(2), BM_img);
        fadeBM.setFromValue(0);
        fadeBM.setToValue(1);
        fadeBM.setCycleCount(FadeTransition.INDEFINITE);
        fadeBM.setAutoReverse(true);
        fadeBM.play();

        FadeTransition fadeData = new FadeTransition(Duration.seconds(2), data_img);
        fadeData.setFromValue(0);
        fadeData.setToValue(1);
        fadeData.setCycleCount(FadeTransition.INDEFINITE);
        fadeData.setAutoReverse(true);
        fadeData.play();

        FadeTransition fadeCash = new FadeTransition(Duration.seconds(2), cash_img);
        fadeCash.setFromValue(0);
        fadeCash.setToValue(1);
        fadeCash.setCycleCount(FadeTransition.INDEFINITE);
        fadeCash.setAutoReverse(true);
        fadeCash.play();
    }

    private void dropText() {
        TranslateTransition translateSup = new TranslateTransition(Duration.seconds(1), sup_text);
        translateSup.setByY(100); // Move down by 100 units
        translateSup.setDelay(Duration.seconds(0.5));
        translateSup.play();

        TranslateTransition translateBM = new TranslateTransition(Duration.seconds(1), bm_text);
        translateBM.setByY(100);
        translateBM.setDelay(Duration.seconds(1));
        translateBM.play();

        TranslateTransition translateCsh = new TranslateTransition(Duration.seconds(1), csh_text);
        translateCsh.setByY(100);
        translateCsh.setDelay(Duration.seconds(1.5));
        translateCsh.play();

        TranslateTransition translateDt = new TranslateTransition(Duration.seconds(1), dt_text);
        translateDt.setByY(100);
        translateDt.setDelay(Duration.seconds(2));
        translateDt.play();
    }

    // Call all animations
    public void initialize() {

        fadeInAndOutImages();
        dropText();
        cancel_btn.setOnAction(this::cance_action);

    }

    public void cance_action(ActionEvent actionEvent) {

        Stage stg = (Stage) cancel_btn.getScene().getWindow();
        stg.close();

    }

    public void csh_action(ActionEvent actionEvent) {
        try {
            System.out.println("Cashier button clicked!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/cashier_login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void data_btn(ActionEvent actionEvent) {
        try {
            System.out.println("Data Entry button clicked!");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/project_pos/Data_entry_operator_login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bm_action(ActionEvent actionEvent) {
        try {
            System.out.println("Branch Manager button clicked!");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/project_pos/branch_manager_login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sa_action(ActionEvent actionEvent) {
        try {
            System.out.println("Super Admin button clicked!");
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/project_pos/super_admin_login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();

            Stage newStage = new Stage();
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
