package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Branch;
import model.BranchManager;
import model.Cashier;
import model.DataEntryOperator;
import services.BranchDAO;
import services.BranchManagerDAO;
import services.CashierDAO;
import services.DataEntryOperatorDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuperAdminController
{
    public ImageView editBranchDetailsImage;



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

    public void create_manager(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/new_manager.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add New Branch Manager");
        stage.setScene(new Scene(root));
        stage.show();


    }

    public void edit_branch(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/project_pos/edit_branch.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add New Branch Manager");
        stage.setScene(new Scene(root));
        stage.show();


    }
}
