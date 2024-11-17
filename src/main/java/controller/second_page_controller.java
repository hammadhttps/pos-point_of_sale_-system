package controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class second_page_controller {

    @FXML
    private Label sup_text, bm_text, csh_text, dt_text;

    @FXML
    private ImageView supadmin_img, BM_img, data_img, cash_img;

    @FXML
    private AnchorPane anchorPane;

    // Fade transition for images
    private void fadeInAndOutImages() {
        FadeTransition fadeSupAdmin = new FadeTransition(Duration.seconds(2), supadmin_img);
        fadeSupAdmin.setFromValue(0);
        fadeSupAdmin.setToValue(1);
        fadeSupAdmin.setCycleCount(1);
        fadeSupAdmin.setAutoReverse(true);
        fadeSupAdmin.play();

        FadeTransition fadeBM = new FadeTransition(Duration.seconds(2), BM_img);
        fadeBM.setFromValue(0);
        fadeBM.setToValue(1);
        fadeBM.setCycleCount(1);
        fadeBM.setAutoReverse(true);
        fadeBM.play();

        FadeTransition fadeData = new FadeTransition(Duration.seconds(2), data_img);
        fadeData.setFromValue(0);
        fadeData.setToValue(1);
        fadeData.setCycleCount(1);
        fadeData.setAutoReverse(true);
        fadeData.play();

        FadeTransition fadeCash = new FadeTransition(Duration.seconds(2), cash_img);
        fadeCash.setFromValue(0);
        fadeCash.setToValue(1);
        fadeCash.setCycleCount(1);
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
        // Fade images in and out
        fadeInAndOutImages();

        // Drop the text labels one after another
        dropText();
    }
}
