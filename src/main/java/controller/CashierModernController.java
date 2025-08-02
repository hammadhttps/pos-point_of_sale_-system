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
import javafx.scene.shape.Circle;
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
    @FXML private Label cartItemCountLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label discountLabel;
    @FXML private Label taxLabel;
    @FXML private Label finalTotalLabel;
    @FXML private Label statusLabel;
    @FXML private Label connectionStatusLabel;
    @FXML private TextField searchField;

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
    private double taxRate = 0.10; // 10% tax
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
        setupSearchField();
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
        colProductPrice.setCellFactory(column -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });
        
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
        colProductTotal.setCellFactory(column -> new TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double total, boolean empty) {
                super.updateItem(total, empty);
                if (empty || total == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", total));
                }
            }
        });

        selectedItemsListtable.setItems(selectedProducts);
    }

    private void setupEventHandlers() {
        // Category buttons
        btnGrocery.setOnAction(event -> showProductsByCategory("Grocery"));
        });
            resetCategoryButtons();
            resetCategoryButtons();
            btnElectronics.getStyleClass().add("category-btn:selected");
            btnGrocery.getStyleClass().add("category-btn:selected");
        });
        btnElectronics.setOnAction(event -> showProductsByCategory("Electronic Accessories"));
            resetCategoryButtons();
            btnfashion.getStyleClass().add("category-btn:selected");
        btnfashion.setOnAction(event -> showProductsByCategory("Fashion"));
        });
        
        // Action buttons
        btnCancel1.setOnAction(event -> deleteSelectedItem());
        btnCancel.setOnAction(event -> clearCart());
        btnCreateBill.setOnAction(event -> createBill());
        btnApplyDiscount.setOnAction(event -> applyDiscount());
        btnSearch.setOnAction(event -> showSearchDialog());
    }

    private void resetCategoryButtons() {
        btnGrocery.getStyleClass().removeAll("category-btn:selected");
        btnElectronics.getStyleClass().removeAll("category-btn:selected");
        btnfashion.getStyleClass().removeAll("category-btn:selected");
    }

    private void setupSearchField() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 2) {
                performSearch();
            } else if (newValue == null || newValue.isEmpty()) {
                if (!currentCategory.isEmpty()) {
                    showProductsByCategory(currentCategory);
                }
            }
        });
    }

    private void performSearch() {
        String searchTerm = searchField.getText();
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            List<Product> searchResults = productDAO.searchProductsByName(searchTerm.trim());
            showSearchResults(searchResults);
        }
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
        connectionStatusLabel.setText("Connected");
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
        if (cashier != null) {
            cashierNameLabel.setText("Cashier: " + cashier.getName());
        }
    }

    public void get_cashier_detail(String username) {
        cashier = cashierDAO.getCashier(username);
        setCashier(cashier);
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
            
            // Create product grid (3 columns)
            VBox gridContainer = new VBox(12);
            HBox currentRow = null;
            int itemsInRow = 0;
            
            for (Product product : products) {
                if (itemsInRow == 0) {
                    currentRow = new HBox(12);
                    currentRow.setAlignment(javafx.geometry.Pos.CENTER);
                    gridContainer.getChildren().add(currentRow);
                }
                
                VBox productCard = createProductCard(product);
                currentRow.getChildren().add(productCard);
                itemsInRow++;
                
                if (itemsInRow == 3) {
                    itemsInRow = 0;
                }
            }
            
            vboxProductContainer.getChildren().add(gridContainer);
            updateStatus("Showing " + category + " products");
        } else {
            productCountLabel.setText("0 items");
            showEmptyState("No products found in " + category);
            updateStatus("No products found in " + category);
        }
    }

    private void showEmptyState(String message) {
        VBox emptyState = new VBox(16);
        emptyState.setAlignment(javafx.geometry.Pos.CENTER);
        emptyState.getStyleClass().add("p-4");
        
        ImageView icon = new ImageView();
        icon.setFitWidth(48);
        icon.setFitHeight(48);
        icon.setOpacity(0.5);
        
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("body-md");
        
        emptyState.getChildren().addAll(icon, messageLabel);
        vboxProductContainer.getChildren().add(emptyState);
    }
    private VBox createProductCard(Product product) {
        VBox card = new VBox(8);
        card.getStyleClass().addAll("product-card");
        card.setPrefWidth(120);
        card.setMaxWidth(120);
        card.setOnMouseClicked(event -> addProductToCart(product));

        // Product Image
        ImageView imageView = createProductImageView(product);
        
        // Product Info
        VBox infoBox = new VBox(4);
        
        Label nameLabel = new Label(product.getName());
        nameLabel.getStyleClass().add("product-name");
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(120);
        
        Label priceLabel = new Label("$" + String.format("%.2f", product.getSalePrice()));
        priceLabel.getStyleClass().add("product-price");
        
        Label categoryLabel = new Label(product.getCategory());
        categoryLabel.getStyleClass().add("product-category");
        
        // Stock indicator
        Label stockLabel = new Label();
        if (product.isInStock()) {
            stockLabel.setText("In Stock (" + product.getQuantity() + ")");
            stockLabel.getStyleClass().add("body-sm");
        } else {
            stockLabel.setText("Out of Stock");
            stockLabel.getStyleClass().addAll("body-sm", "danger-text");
            card.setDisable(true);
            card.setOpacity(0.6);
        }
        
        infoBox.getChildren().addAll(nameLabel, priceLabel, categoryLabel, stockLabel);
        
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
                // Use a default placeholder
                imagePath = "/com/example/project_pos/icons_and_images/add-to-cart.png";
                image = new Image(getClass().getResourceAsStream(imagePath));
            }
            imageView.setImage(image);
        } catch (Exception e) {
            // Create a simple placeholder
            imageView.setStyle("-fx-background-color: #f1f5f9; -fx-background-radius: 8px;");
        }
        
        return imageView;
    }

    private void addProductToCart(Product product) {
        if (!product.isInStock()) {
            showAlert("Out of Stock", product.getName() + " is currently out of stock.");
            return;
        }
        
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
        if (selectedProducts.isEmpty()) {
            showAlert("Information", "Cart is already empty");
            return;
        }
        
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
        
        discountAmount = subtotal * (discountPercentage / 100.0);
        double taxAmount = (subtotal - discountAmount) * taxRate;
        double finalTotal = subtotal - discountAmount + taxAmount;
        
        int totalItems = productSelectionCount.values().stream().mapToInt(Integer::intValue).sum();
        
        // Update UI labels
        subtotalLabel.setText("$" + String.format("%.2f", subtotal));
        discountLabel.setText("$" + String.format("%.2f", discountAmount));
        taxLabel.setText("$" + String.format("%.2f", taxAmount));
        finalTotalLabel.setText("$" + String.format("%.2f", finalTotal));
        cartTotalLabel.setText("Total: $" + String.format("%.2f", finalTotal));
        cartItemCountLabel.setText(String.valueOf(totalItems));
        
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


    private void showSearchResults(List<Product> results) {
        vboxProductContainer.getChildren().clear();
        productCountLabel.setText(results.size() + " items found");
        
        if (results.isEmpty()) {
            showEmptyState("No products found matching your search");
        } else {
            // Create product grid for search results
            VBox gridContainer = new VBox(12);
            HBox currentRow = null;
            int itemsInRow = 0;
            
            for (Product product : results) {
                if (itemsInRow == 0) {
                    currentRow = new HBox(12);
                    currentRow.setAlignment(javafx.geometry.Pos.CENTER);
                    gridContainer.getChildren().add(currentRow);
                }
                
                VBox productCard = createProductCard(product);
                currentRow.getChildren().add(productCard);
                itemsInRow++;
                
                if (itemsInRow == 3) {
                    itemsInRow = 0;
                }
            }
            
            vboxProductContainer.getChildren().add(gridContainer);
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
            double taxAmount = (subtotal - discountAmount) * taxRate;
            double finalTotal = subtotal - discountAmount + taxAmount;
            
            // Create a new list of products with quantities
            List<Product> saleProducts = new ArrayList<>();
            for (Product product : selectedProducts) {
                int quantity = productSelectionCount.getOrDefault(product, 0);
                Product saleProduct = new Product(product.getProductId(), product.getName(), 
                                               product.getCategory(), product.getOriginalPrice(), 
                                               product.getSalePrice(), product.getPriceByUnit(), 
                                               product.getPriceByCarton(), quantity);
                saleProduct.setSoldQuantity(quantity);
                saleProducts.add(saleProduct);
            }
            
            Sale sale = new Sale(saleId, saleProducts, finalTotal, new Date(), cashier.getBranchCode());
            
            // Save to database
            boolean success = saleDAO.createSale(sale);
            
            if (success) {
                // Update product quantities in inventory
                productDAO.updateProductQuantities(productSelectionCount);
                
                showAlert("Success", "Bill created successfully!\nSale ID: " + sale.getSaleId());
                clearCart();
                updateStatus("Bill created successfully");
                
                // Refresh current category to show updated stock
                if (!currentCategory.isEmpty()) {
                    loadProducts(); // Reload to get updated quantities
                    showProductsByCategory(currentCategory);
                }
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

    public void handleLogout(javafx.event.ActionEvent event) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Logout Confirmation");
        confirmAlert.setHeaderText("Are you sure you want to logout?");
        confirmAlert.setContentText("Any unsaved changes will be lost.");
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cleanup();
            // Navigate back to login screen
            javafx.application.Platform.exit();
        }
    }

    public void handleLogout(javafx.scene.input.MouseEvent event) {
        handleLogout(new javafx.event.ActionEvent());
    }

    public void cleanup() {
        if (updateTimer != null) {
            updateTimer.stop();
        }
    }
}