package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Cashier {

    @FXML
    private Button btnGrocery, btnElectronics, btnBakery;

    @FXML
    private VBox vboxProductContainer;

    @FXML
    public void initialize() {
        btnGrocery.setOnAction(event -> showGroceryItems());
        btnElectronics.setOnAction(event -> showElectronicItems());
        btnBakery.setOnAction(event -> showBakeryItems());
    }

    private void showGroceryItems() {
        vboxProductContainer.getChildren().clear();
        vboxProductContainer.getChildren().addAll(
                createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g1.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g2.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g3.png")
                ),
                createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g4.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g5.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g6.png")
                ),createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g7.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g8.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g9.png")
                )
                ,createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g10.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g11.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g12.png")
                ),
                createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g13.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g14.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g15.png")
                ),createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g16.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g17.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g18.png")
                )
        );
    }

    private void showElectronicItems() {
        vboxProductContainer.getChildren().clear();
        vboxProductContainer.getChildren().addAll(
                createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\airpods.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\laptop.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\headphones.png")
                ),
                createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\smart-watch.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\technology.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\sun-glasses.png")
                )
        );
    }

    private void showBakeryItems() {
        vboxProductContainer.getChildren().clear();
        vboxProductContainer.getChildren().addAll(
                createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g7.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g8.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g9.png")
                ),
                createRow(
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g10.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g11.png"),
                        createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g12.png")
                ),createRow(

                )
        );
    }

    private HBox createRow(ImageView... images) {
        HBox row = new HBox(15); // 10px spacing between images
        row.getChildren().addAll(images);
        return row;
    }

    private ImageView createImageView(String imagePath) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
