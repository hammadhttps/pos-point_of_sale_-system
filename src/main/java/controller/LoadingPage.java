package controller;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoadingPage {

    @FXML
    private Label loading;

    @FXML
    private Line line1, line2, line3, line4;

    @FXML
    private AnchorPane anchorPane;

    private void animateLines() {
        Timeline lineAnimation = new Timeline();


        KeyFrame line1Animation = new KeyFrame(Duration.seconds(2), e -> {
            if (line1.getEndX() < line2.getLayoutX()) {
                line1.setEndX(line1.getEndX() + 1000);
            }
        });

        KeyFrame line2Animation = new KeyFrame(Duration.seconds(4), e -> {
            if (line2.getEndY() < line3.getLayoutY()) {
                line2.setEndY(line2.getEndY() + 500);
            }
        });

        KeyFrame line3Animation = new KeyFrame(Duration.seconds(6), e -> {
            if (line3.getEndX() > -900) {
                line3.setEndX(line3.getEndX() - 1000);
            }
        });

        KeyFrame line4Animation = new KeyFrame(Duration.seconds(8), e -> {
            if (line4.getEndY() > -900) {
                line4.setEndY(line4.getEndY() - 500);
            }
        });

        // Add all keyframes in sequence
        lineAnimation.getKeyFrames().addAll(line1Animation, line2Animation, line3Animation, line4Animation);
        lineAnimation.setCycleCount(1); // Run the animation once
        lineAnimation.setRate(2);

        // After animation completes, switch to the second screen
        lineAnimation.setOnFinished(event -> {
            showSecondScreen();
        });

        lineAnimation.play();

        // Fade-in, fade-out effect for the "loading" text
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), loading);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    private void showSecondScreen() {
        try {
            // Load the second screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/second_screen.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) anchorPane.getScene().getWindow();

            // Set the new scene with the second screen
            Scene scene = new Scene(root);
            stage.setScene(scene);


            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {

        animateLines();
    }
}
