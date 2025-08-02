package controller;

import model.Cashier;
import model.Product;
import model.Sale;
import services.CashierDAO;
import services.SaleDAO;
import services.ProductDAO;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.*;

public class CashierModernController {

    // FXML Components
    @FXML private Label cashierNameLabel;
    @FXML private Label currentTimeLabel;
    @FXML private Label currentDateLabel;
    @FXML private Label productCountLabel;
    @FXML private Label cartTotalLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label discountLabel;
    @FXML private Label finalTotalLabel;
    @FXML private Label statusLabel;
    @FXML private Label connectionStatusLabel;

    @FXML private Button btnGrocery, btnElectronics, btnfashion, btnSearch;
    @FXML private Button btnCancel, btnCreateBill, btnCancel1, btnApplyDiscount;

    @FXML private ScrollPane scrollPane;
    @FXML private VBox vboxProductContainer;
    @FXML private TableView<Product> selectedItemsListtable;

    @FXML private TableColumn<Product, String> colProductName, colProductCategory;
    @FXML private TableColumn<Product, Double> colProductPrice;
    @FXML private TableColumn<Product, Integer> colProductquantity;
    @FXML private TableColumn<Product, Double> colProductTotal;

    // Data Management
    private final SaleDAO saleDAO = new SaleDAO();
    private final CashierDAO cashierDAO = new CashierDAO();
    private final ProductDAO productDAO = new ProductDAO();
    
    private Cashier cashier = null;
    private final ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
    private final Map<String, List<Product>> productsByCategory = new HashMap<>();
    private final Map<Product, Integer> productSelectionCount = new HashMap<>();
    
    private double subtotal = 0.0;
    private double discountAmount = 0.0;
    private double discountPercentage = 0.0;
    private String currentCategory = "";

    // UI Update Timer
    private final Timeline updateTimer = new Timeline(
        new KeyFrame(Duration.seconds(1), event -> updateDateTime())
    );

    @FXML
    public void initialize() {
        setupTableColumns();
        setupEventHandlers();
        loadProducts();
        startUpdateTimer();
        updateConnectionStatus();
    }

    private void setupTableColumns() {
        // Product Name Column
        colProductName.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getName()));
        
        // Category Column
        colProductCategory.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getCategory()));
        
        // Price Column
        colProductPrice.setCellValueFactory(data -> 
            new SimpleDoubleProperty(data.getValue().getSalePrice()).asObject());
        
        // Quantity Column
        colProductquantity.setCellValueFactory(data ->
            new SimpleIntegerProperty(productSelectionCount.getOrDefault(data.getValue(), 0)).asObject());
        
        // Total Column
        colProductTotal.setCellValueFactory(data -> {
            Product product = data.getValue();
            int quantity = productSelectionCount.getOrDefault(product, 0);
            double total = product.getSalePrice() * quantity;
            return new SimpleDoubleProperty(total).asObject();
        });

        selectedItemsListtable.setItems(selectedProducts);
    }

    private void setupEventHandlers() {
        // Category buttons
        btnGrocery.setOnAction(event -> showProductsByCategory("Grocery"));
        btnElectronics.setOnAction(event -> showProductsByCategory("Electronic Accessories"));
        btnfashion.setOnAction(event -> showProductsByCategory("Fashion"));
        
        // Action buttons
        btnCancel1.setOnAction(event -> deleteSelectedItem());
        btnCancel.setOnAction(event -> clearCart());
        btnCreateBill.setOnAction(event -> createBill());
        btnApplyDiscount.setOnAction(event -> applyDiscount());
        btnSearch.setOnAction(event -> showSearchDialog());
    }

    private void startUpdateTimer() {
        updateTimer.setCycleCount(Timeline.INDEFINITE);
        updateTimer.play();
        updateDateTime();
    }

    private void updateDateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        
        currentTimeLabel.setText("Time: " + timeFormat.format(new Date()));
        currentDateLabel.setText("Date: " + dateFormat.format(new Date()));
    }

    private void updateConnectionStatus() {
        // Simulate connection check - in real app, check actual DB connection
        connectionStatusLabel.setText("Connected");
        connectionStatusLabel.getStyleClass().add("connected");
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
        if (cashier != null) {
            cashierNameLabel.setText("Cashier: " + cashier.getName());
        }
    }

    private void loadProducts() {
        try {
            List<Product> allProducts = productDAO.getAllProducts();
            
            // Group products by category
            for (Product product : allProducts) {
                String category = product.getCategory();
                productsByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(product);
            }
            
            updateStatus("Products loaded successfully");
        } catch (Exception e) {
            updateStatus("Error loading products: " + e.getMessage());
            showAlert("Error", "Failed to load products");
        }
    }

    private void showProductsByCategory(String category) {
        currentCategory = category;
        vboxProductContainer.getChildren().clear();
        
        List<Product> products = productsByCategory.get(category);
        if (products != null) {
            productCountLabel.setText(products.size() + " items");
            
            for (Product product : products) {
                VBox productCard = createProductCard(product);
                vboxProductContainer.getChildren().add(productCard);
            }
            
            updateStatus("Showing " + category + " products");
        } else {
            productCountLabel.setText("0 items");
            updateStatus("No products found in " + category);
        }
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox(8);
        card.getStyleClass().add("product-card");
        card.setOnMouseClicked(event -> addProductToCart(product));

        // Product Image
        ImageView imageView = createProductImageView(product);
        
        // Product Info
        VBox infoBox = new VBox(4);
        infoBox.getStyleClass().add("product-info");
        
        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("product-name");
        
        Label priceLabel = new Label("$" + String.format("%.2f", product.getSalePrice()));
        priceLabel.getStyleClass().add("product-price");
        
        Label categoryLabel = new Label(product.getCategory());
        categoryLabel.getStyleClass().add("product-category");
        
        infoBox.getChildren().addAll(nameLabel, priceLabel, categoryLabel);
        
        card.getChildren().addAll(imageView, infoBox);
        
        return card;
    }

    private ImageView createProductImageView(Product product) {
        ImageView imageView = new ImageView();
        imageView.getStyleClass().add("product-image");
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        
        try {
            // Try to load product image
            String imagePath = "/com/example/project_pos/products_icons/" + product.getName().toLowerCase().replace(" ", "_") + ".png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            if (image.isError()) {
                // Use default image if product image not found
                image = new Image(getClass().getResourceAsStream("/com/example/project_pos/products_icons/default_product.png"));
            }
            imageView.setImage(image);
        } catch (Exception e) {
            // Use a placeholder if image loading fails
            imageView.setStyle("-fx-background-color: #E2E8F0; -fx-background-radius: 4px;");
        }
        
        return imageView;
    }

    private void addProductToCart(Product product) {
        int currentCount = productSelectionCount.getOrDefault(product, 0);
        productSelectionCount.put(product, currentCount + 1);
        
        if (!selectedProducts.contains(product)) {
            selectedProducts.add(product);
        }
        
        updateCartTotals();
        updateStatus("Added " + product.getName() + " to cart");
    }

    private void deleteSelectedItem() {
        Product selectedProduct = selectedItemsListtable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            int currentCount = productSelectionCount.getOrDefault(selectedProduct, 0);
            if (currentCount > 1) {
                productSelectionCount.put(selectedProduct, currentCount - 1);
            } else {
                productSelectionCount.remove(selectedProduct);
                selectedProducts.remove(selectedProduct);
            }
            updateCartTotals();
            updateStatus("Removed " + selectedProduct.getName() + " from cart");
        } else {
            showAlert("Warning", "Please select an item to remove");
        }
    }

    private void clearCart() {
        selectedProducts.clear();
        productSelectionCount.clear();
        updateCartTotals();
        updateStatus("Cart cleared");
    }

    private void updateCartTotals() {
        subtotal = 0.0;
        
        for (Product product : selectedProducts) {
            int quantity = productSelectionCount.getOrDefault(product, 0);
            subtotal += product.getSalePrice() * quantity;
        }
        
        double discountValue = subtotal * (discountPercentage / 100.0);
        discountAmount = discountValue;
        double finalTotal = subtotal - discountAmount;
        
        // Update UI labels
        subtotalLabel.setText("$" + String.format("%.2f", subtotal));
        discountLabel.setText("$" + String.format("%.2f", discountAmount));
        finalTotalLabel.setText("$" + String.format("%.2f", finalTotal));
        cartTotalLabel.setText("Total: $" + String.format("%.2f", finalTotal));
        
        // Refresh table
        selectedItemsListtable.refresh();
    }

    private void applyDiscount() {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(discountPercentage));
        dialog.setTitle("Apply Discount");
        dialog.setHeaderText("Enter discount percentage");
        dialog.setContentText("Discount (%):");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> {
            try {
                double newDiscount = Double.parseDouble(value);
                if (newDiscount >= 0 && newDiscount <= 100) {
                    discountPercentage = newDiscount;
                    updateCartTotals();
                    updateStatus("Discount applied: " + newDiscount + "%");
                } else {
                    showAlert("Invalid Discount", "Please enter a value between 0 and 100");
                }
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid number");
            }
        });
    }

    private void showSearchDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Products");
        dialog.setHeaderText("Enter product name to search");
        dialog.setContentText("Product name:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(searchTerm -> {
            List<Product> searchResults = productDAO.searchProductsByName(searchTerm);
            showSearchResults(searchResults);
        });
    }

    private void showSearchResults(List<Product> results) {
        vboxProductContainer.getChildren().clear();
        productCountLabel.setText(results.size() + " items found");
        
        for (Product product : results) {
            VBox productCard = createProductCard(product);
            vboxProductContainer.getChildren().add(productCard);
        }
        
        updateStatus("Search results: " + results.size() + " products found");
    }

    private void createBill() {
        if (selectedProducts.isEmpty()) {
            showAlert("Empty Cart", "Please add items to cart before checkout");
            return;
        }
        
        try {
            // Create sale record with proper constructor
            String saleId = generateSaleId();
            double finalTotal = subtotal - discountAmount;
            
            // Create a new list of products with quantities
            List<Product> saleProducts = new ArrayList<>();
            for (Product product : selectedProducts) {
                int quantity = productSelectionCount.getOrDefault(product, 0);
                Product saleProduct = new Product(product.getProductId(), product.getName(), 
                                               product.getCategory(), product.getOriginalPrice(), 
                                               product.getSalePrice(), product.getPriceByUnit(), 
                                               product.getPriceByCarton(), quantity);
                saleProducts.add(saleProduct);
            }
            
            Sale sale = new Sale(saleId, saleProducts, finalTotal, new Date(), cashier.getBranchCode());
            
            // Save to database
            boolean success = saleDAO.createSale(sale);
            
            if (success) {
                showAlert("Success", "Bill created successfully!\nSale ID: " + sale.getSaleId());
                clearCart();
                updateStatus("Bill created successfully");
            } else {
                showAlert("Error", "Failed to create bill");
            }
            
        } catch (Exception e) {
            showAlert("Error", "Failed to create bill: " + e.getMessage());
            updateStatus("Error creating bill");
        }
    }

    private String generateSaleId() {
        return "SALE-" + System.currentTimeMillis();
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void cleanup() {
        if (updateTimer != null) {
            updateTimer.stop();
        }
    }
} 