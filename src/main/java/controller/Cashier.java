package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Product;

public class Cashier {

    public Button btnCancel;
    public Button btnAddItem;
    public Button btnCreateBill;
    public Button btnAddItem1;
    public Button btnCancel1;
    public ListView selectedItemsList;
    public ScrollPane scrollPane;
    @FXML
    private Button btnGrocery, btnElectronics, btnBakery;

    @FXML
    private VBox vboxProductContainer;

    @FXML
    private TableView<Product>  selectedItemsListtable;

    @FXML
    private TableColumn<Product, String> colProductName, colProductCategory;

    @FXML
    private TableColumn<Product, Double> colProductPrice;

    private final ObservableList<Product> selectedProducts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        colProductName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colProductCategory.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        colProductPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalePrice()).asObject());

        // Bind selected products to the table
        selectedItemsListtable.setItems(selectedProducts);

        // Button actions
        btnGrocery.setOnAction(event -> showGroceryItems());
        btnElectronics.setOnAction(event -> showElectronicItems());
        btnBakery.setOnAction(event -> showBakeryItems());
    }

    private void showGroceryItems() {
        vboxProductContainer.getChildren().clear();
        vboxProductContainer.getChildren().addAll(
                createRow(
                        createProductBox(new Product("1", "Apple", "Grocery", 1.0, 0.9, 0.1, 5.0, 1, 100)),
                        createProductBox(new Product("2", "Banana", "Grocery", 1.2, 1.0, 0.1, 5.0, 1, 50)),
                        createProductBox(new Product("3", "Carrot", "Grocery", 0.8, 0.7, 0.05, 3.0, 1, 80))
                )
        );
    }

    private void showElectronicItems() {
        vboxProductContainer.getChildren().clear();
        vboxProductContainer.getChildren().addAll(
                createRow(
                        createProductBox(new Product("4", "Laptop", "Electronics", 1200.0, 1000.0, 0.0, 0.0, 1, 10)),
                        createProductBox(new Product("5", "Headphones", "Electronics", 200.0, 150.0, 0.0, 0.0, 1, 30)),
                        createProductBox(new Product("6", "Smartwatch", "Electronics", 300.0, 250.0, 0.0, 0.0, 1, 20))
                )
        );
    }

    private void showBakeryItems() {
        vboxProductContainer.getChildren().clear();
        vboxProductContainer.getChildren().addAll(
                createRow(
                        createProductBox(new Product("7", "Bread", "Bakery", 1.5, 1.3, 0.1, 2.0, 1, 100)),
                        createProductBox(new Product("8", "Cake", "Bakery", 20.0, 18.0, 0.0, 0.0, 1, 15)),
                        createProductBox(new Product("9", "Pastry", "Bakery", 3.0, 2.5, 0.2, 4.0, 1, 50))
                )
        );
    }

    private HBox createRow(VBox... products) {
        HBox row = new HBox(15); // 15px spacing
        row.getChildren().addAll(products);
        return row;
    }

    private VBox createProductBox(Product product) {
        VBox productBox = new VBox(5); // 5px spacing
        productBox.setStyle("-fx-alignment: center;");

        ImageView imageView = createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\g1.png");
        Label nameLabel = new Label(product.getName());
        Label priceLabel = new Label("$" + product.getSalePrice());

        imageView.setOnMouseClicked(event -> addProductToSelectedList(product));

        productBox.getChildren().addAll(imageView, nameLabel, priceLabel);
        return productBox;
    }

    private ImageView createImageView(String imagePath) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private void addProductToSelectedList(Product product) {
        selectedProducts.add(product);
    }
}
