package controller;

import DB.InternetConnectionChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Product;
import services.ProductDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddProduct {

    public AnchorPane addProductPane;
    ProductDAO productDAO = new ProductDAO();
    InternetConnectionChecker internetChecker = new InternetConnectionChecker();
    private static final String OFFLINE_DATA_FILE = "offline_products.txt";

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
    private Button uploadOfflineDataButton;

    @FXML
    public void initialize() {
        checkForOfflineData();
    }

    @FXML
    public void addProduct(ActionEvent event) {
        // Retrieve values from input fields
        String productId = productIdField.getText();
        String productName = productNameField.getText();
        String category = categoryField.getValue();
        String originalPrice = originalPriceField.getText();
        String salePrice = salePriceField.getText();
        String priceByUnit = priceByUnitField.getText();
        String priceByCarton = priceByCartonField.getText();
        String quantity = quantityField.getText();

        //  Check if any field is empty
        if (productId.isEmpty() || productName.isEmpty() || category == null ||
                originalPrice.isEmpty() || salePrice.isEmpty() ||
                priceByUnit.isEmpty() || priceByCarton.isEmpty() || quantity.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("Incomplete Product Details");
            alert.setContentText("Please fill out all fields before adding the product.");
            alert.showAndWait();
            return;
        }

        Product product = new Product(productId, productName, category, Double.parseDouble(originalPrice),
                Double.parseDouble(salePrice), Double.parseDouble(priceByUnit), Double.parseDouble(priceByCarton),
                Integer.parseInt(quantity));

        if (internetChecker.isInternetAvailable()) {
            // If internet is available, add product to the database
            String result = productDAO.addProduct(product);
            showAlert(Alert.AlertType.INFORMATION, "Product Added", result);
        } else {
            // If no internet, save data locally
            saveProductOffline(product);
            showAlert(Alert.AlertType.WARNING, "Offline Mode", "Internet not available. Product saved offline.");
        }

        clearFields();
    }

    private void saveProductOffline(Product product) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OFFLINE_DATA_FILE, true))) {
            writer.write(product.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkForOfflineData() {
        File file = new File(OFFLINE_DATA_FILE);
        if (file.exists() && file.length() > 0) {
            uploadOfflineDataButton.setVisible(true);
        } else {
            uploadOfflineDataButton.setVisible(false);
        }
    }

    @FXML
    public void uploadOfflineData(ActionEvent event) {
        List<Product> offlineProducts = loadOfflineData();
        if (internetChecker.isInternetAvailable()) {
            for (Product product : offlineProducts) {
                productDAO.addProduct(product);
            }
            clearOfflineDataFile();
            showAlert(Alert.AlertType.INFORMATION, "Offline Data Uploaded", "All offline products have been uploaded.");
            uploadOfflineDataButton.setVisible(false);
        } else {
            showAlert(Alert.AlertType.WARNING, "Offline Mode", "Internet not available. Cannot upload offline data.");
        }
    }

    private List<Product> loadOfflineData() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(OFFLINE_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                products.add(Product.parse(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    private void clearOfflineDataFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OFFLINE_DATA_FILE))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        productIdField.clear();
        productNameField.clear();
        categoryField.setValue(null);
        originalPriceField.clear();
        salePriceField.clear();
        priceByUnitField.clear();
        priceByCartonField.clear();
        quantityField.clear();
    }
}
