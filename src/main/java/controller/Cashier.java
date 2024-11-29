package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import services.ProductDAO;

import java.util.*;

public class Cashier {

    public Button btnCancel;
    public Button btnCreateBill;
    public Button btnAddItem1;
    public Button btnCancel1;
    public ListView selectedItemsList;
    public ScrollPane scrollPane;
    //public TableColumn colProductquantity;
    @FXML
    private Button btnGrocery, btnElectronics, btnfashion;

    @FXML
    private VBox vboxProductContainer;

    @FXML
    private TableView<Product> selectedItemsListtable;

    @FXML
    private TableColumn<Product, String> colProductName, colProductCategory;

    @FXML
    private TableColumn<Product, Double> colProductPrice;
    @FXML
    private TableColumn<Product, Integer> colProductquantity;


    private final ObservableList<Product> selectedProducts = FXCollections.observableArrayList();

    // Map to store products by category
    private Map<String, List<Product>> productsByCategory = new HashMap<>();
    private final Map<Product, Integer> productSelectionCount = new HashMap<>();

    @FXML
    public void initialize() {

        // Initialize table columns
        colProductName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        colProductCategory.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));
        colProductPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalePrice()).asObject());
        colProductquantity.setCellValueFactory(data ->
                new SimpleIntegerProperty(productSelectionCount.getOrDefault(data.getValue(), 0)).asObject()
        );


        // Bind selected products to the table
        selectedItemsListtable.setItems(selectedProducts);

        // Load products once from the database
        loadProducts();

        // Button actions
        btnGrocery.setOnAction(event -> showProductsByCategory("Grocery"));
        btnElectronics.setOnAction(event -> showProductsByCategory("Electronic Accessories"));
        btnfashion.setOnAction(event -> showProductsByCategory("Fashion"));
        btnCancel1.setOnAction(event -> deleteSelectedItem());

        // Set action for Cancel button
        btnCancel.setOnAction(event -> resetTable());
    }

    private void loadProducts() {
        ProductDAO productDAO = new ProductDAO();

        // Fetch products from the database for all categories
        List<Product> allProducts = productDAO.getAllProducts();

        // Cache products by category
        for (Product product : allProducts) {
            productsByCategory.computeIfAbsent(product.getCategory(), k -> new ArrayList<>()).add(product);
        }
    }

    private void showProductsByCategory(String category) {
        // Get the cached products for the selected category
        List<Product> products = productsByCategory.get(category);

        if (products != null) {
            vboxProductContainer.getChildren().clear();
            List<HBox> productRows = new ArrayList<>();

            // Create rows of 3 products
            for (int i = 0; i < products.size(); i += 3) {
                List<VBox> productBoxes = new ArrayList<>();
                for (int j = i; j < i + 3 && j < products.size(); j++) {
                    productBoxes.add(createProductBox(products.get(j)));
                }
                productRows.add(createRow(productBoxes.toArray(new VBox[0])));
            }

            vboxProductContainer.getChildren().addAll(productRows);
        } else {
            showAlert("No Products", "No products available in this category.");
        }
    }

    private void deleteSelectedItem() {
        Product selectedProduct = selectedItemsListtable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            int currentCount = productSelectionCount.get(selectedProduct);
            if (currentCount > 1) {
                productSelectionCount.put(selectedProduct, currentCount - 1); // Decrement count
            } else {
                productSelectionCount.remove(selectedProduct); // Remove from map
                selectedProducts.remove(selectedProduct); // Remove from TableView
            }
            selectedItemsListtable.refresh();
        } else {
            showAlert("No Selection", "Please select an item to delete.");
        }
    }


    private void resetTable() {
        selectedProducts.clear(); // Clear all selected products from the list
    }

    private HBox createRow(VBox... products) {
        HBox row = new HBox(15); // 15px spacing between products
        row.getChildren().addAll(products);
        return row;
    }

    private VBox createProductBox(Product product) {
        VBox productBox = new VBox(5); // 5px spacing inside each product box
        productBox.setStyle("-fx-alignment: center;");

        // Using a relative path for the image file (adjust path as needed)
       // ImageView imageView = createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\milk.png");
        ImageView imageView = createImageView("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\"+product.getName()+".png");
        Label nameLabel = new Label(product.getName());
        Label priceLabel = new Label("$" + product.getSalePrice());

        imageView.setOnMouseClicked(event -> addProductToSelectedList(product));

        productBox.getChildren().addAll(imageView, nameLabel, priceLabel);
        return productBox;
    }

    private ImageView createImageView(String imagePath) {
        Image image;
        try {
            image = new Image(imagePath);
            if (image.isError()) {
                throw new IllegalArgumentException("Image not found.");
            }
        } catch (Exception e) {
            // Fallback to default image if the specified image is not found
            image = new Image("C:\\Users\\city\\Desktop\\Project_pos\\src\\main\\resources\\com\\example\\project_pos\\products_icons\\image.png");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
    private void addProductToSelectedList(Product product) {
        if (productSelectionCount.containsKey(product)) {
            int currentCount = productSelectionCount.get(product);
            productSelectionCount.put(product, currentCount + 1); // Increment count
        } else {
            productSelectionCount.put(product, 1); // Add with initial count of 1
            selectedProducts.add(product); // Add to observable list for the TableView
        }

        selectedItemsListtable.refresh(); // Refresh the table to show updated counts
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
