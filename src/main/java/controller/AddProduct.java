package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Product;
import services.ProductDAO;

public class AddProduct {


    ProductDAO productDAO = new ProductDAO();

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private ComboBox<String> categoryField;

    @FXML
    private TextField originalPriceField;

    @FXML
    private TextField salePriceField;

    @FXML
    private TextField priceByUnitField;

    @FXML
    private TextField priceByCartonField;

    @FXML
    private TextField quantityField;

    @FXML
    public void addProduct(ActionEvent event) {
        // Retrieve values from input fields
        String productId = productIdField.getText();
        String productName = productNameField.getText();
        String category = categoryField.getValue(); // Get selected category
        String originalPrice = originalPriceField.getText();
        String salePrice = salePriceField.getText();
        String priceByUnit = priceByUnitField.getText();
        String priceByCarton = priceByCartonField.getText();
        String quantity = quantityField.getText();


        // Validation: Check if any field is empty
        if (productId.isEmpty() || productName.isEmpty() || category.isEmpty() ||
                originalPrice.isEmpty() || salePrice.isEmpty() ||
                priceByUnit.isEmpty() || priceByCarton.isEmpty() || quantity.isEmpty()) {

            // Display an alert notification
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Incomplete Product Details");
            alert.setContentText("Please fill out all fields before adding the product.");
            alert.showAndWait();
            return;
        }
        Product product = new Product(productId, productName, category, Double.parseDouble(originalPrice), Double.parseDouble(salePrice)
                , Double.parseDouble(priceByUnit), Double.parseDouble(priceByCarton), Integer.parseInt(quantity));

        String text = productDAO.addProduct(product);

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Product Addition");
        successAlert.setHeaderText(null);
        successAlert.setContentText(text);
        successAlert.showAndWait();


        // Optionally clear fields after adding the product
        productIdField.clear();
        productNameField.clear();
        categoryField.setValue(null); // Reset ComboBox
        originalPriceField.clear();
        salePriceField.clear();
        priceByUnitField.clear();
        priceByCartonField.clear();
        quantityField.clear();
    }
}

