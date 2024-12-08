package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SuperAdminController
{
    public void create_branch(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/new_branch.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add New Product");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void reports(MouseEvent mouseEvent)
    {

    }

    public void close(MouseEvent mouseEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void create_manager(MouseEvent mouseEvent)
    {

    }

    public void edit_branch(MouseEvent mouseEvent)
    {

    }
}
