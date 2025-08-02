package services;

import DB.DBConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for Product entity.
 * Handles all database operations related to products.
 */
public class ProductDAO extends BaseDAO {

    // SQL Queries
    private static final String CHECK_PRODUCT_EXISTS = "SELECT COUNT(*) FROM product WHERE productId = ?";
    private static final String INSERT_PRODUCT = "INSERT INTO product (productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_PRODUCTS = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity FROM product";
    private static final String UPDATE_PRODUCT_QUANTITY = "UPDATE product SET quantity = quantity - ? WHERE productId = ?";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity FROM product WHERE productId = ?";
    private static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, category = ?, originalPrice = ?, salePrice = ?, priceByUnit = ?, priceByCarton = ?, quantity = ? WHERE productId = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM product WHERE productId = ?";

    /**
     * Add a new product to the database
     * 
     * @param product the product to add
     * @return success message or error message
     */
    public String addProduct(Product product) {
        if (product == null) {
            return "Error: Product cannot be null";
        }

        // Check if product already exists
        if (exists(CHECK_PRODUCT_EXISTS, new Object[] { product.getProductId() })) {
            return "Product already exists, cannot add duplicate";
        }

        // Insert the product
        Object[] params = {
                product.getProductId(),
                product.getName(),
                product.getCategory(),
                product.getOriginalPrice(),
                product.getSalePrice(),
                product.getPriceByUnit(),
                product.getPriceByCarton(),
                product.getQuantity()
        };

        int rowsAffected = executeUpdate(INSERT_PRODUCT, params);

        if (rowsAffected > 0) {
            System.out.println("Product added successfully: " + product.getProductId());
            return SUCCESS_MESSAGE;
        } else {
            return ERROR_MESSAGE;
        }
    }

    /**
     * Get all products from the database
     * 
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        return executeQuery(SELECT_ALL_PRODUCTS, null, this::mapResultSetToProduct);
    }

    /**
     * Get a product by its ID
     * 
     * @param productId the product ID to search for
     * @return the product if found, null otherwise
     */
    public Product getProductById(String productId) {
        List<Product> products = executeQuery(SELECT_PRODUCT_BY_ID, new Object[] { productId },
                this::mapResultSetToProduct);
        return products.isEmpty() ? null : products.get(0);
    }

    /**
     * Update product quantities after a sale
     * 
     * @param soldProducts map of products and their sold quantities
     * @return true if update was successful, false otherwise
     */
    public boolean updateProductQuantities(Map<Product, Integer> soldProducts) {
        if (soldProducts == null || soldProducts.isEmpty()) {
            return true; // Nothing to update
        }

        List<Object[]> batchParams = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : soldProducts.entrySet()) {
            Product product = entry.getKey();
            Integer soldQuantity = entry.getValue();

            if (product != null && soldQuantity != null && soldQuantity > 0) {
                batchParams.add(new Object[] { soldQuantity, product.getProductId() });
            }
        }

        if (batchParams.isEmpty()) {
            return true;
        }

        int[] results = executeBatchUpdate(UPDATE_PRODUCT_QUANTITY, batchParams);

        // Check if all updates were successful
        for (int result : results) {
            if (result <= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Update an existing product
     * 
     * @param product the product to update
     * @return true if update was successful, false otherwise
     */
    public boolean updateProduct(Product product) {
        if (product == null) {
            return false;
        }

        Object[] params = {
                product.getName(),
                product.getCategory(),
                product.getOriginalPrice(),
                product.getSalePrice(),
                product.getPriceByUnit(),
                product.getPriceByCarton(),
                product.getQuantity(),
                product.getProductId()
        };

        int rowsAffected = executeUpdate(UPDATE_PRODUCT, params);
        return rowsAffected > 0;
    }

    /**
     * Delete a product by its ID
     * 
     * @param productId the product ID to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteProduct(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return false;
        }

        int rowsAffected = executeUpdate(DELETE_PRODUCT, new Object[] { productId });
        return rowsAffected > 0;
    }

    /**
     * Search products by name (case-insensitive partial match)
     * 
     * @param searchTerm the search term
     * @return list of matching products
     */
    public List<Product> searchProductsByName(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String searchQuery = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity FROM product WHERE LOWER(name) LIKE LOWER(?)";
        return executeQuery(searchQuery, new Object[] { "%" + searchTerm + "%" }, this::mapResultSetToProduct);
    }

    /**
     * Get products by category
     * 
     * @param category the category to filter by
     * @return list of products in the specified category
     */
    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return new ArrayList<>();
        }

        String categoryQuery = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity FROM product WHERE category = ?";
        return executeQuery(categoryQuery, new Object[] { category }, this::mapResultSetToProduct);
    }

    /**
     * Get low stock products (quantity less than threshold)
     * 
     * @param threshold the quantity threshold
     * @return list of products with low stock
     */
    public List<Product> getLowStockProducts(int threshold) {
        String lowStockQuery = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity FROM product WHERE quantity < ?";
        return executeQuery(lowStockQuery, new Object[] { threshold }, this::mapResultSetToProduct);
    }

    /**
     * Map ResultSet row to Product object
     * 
     * @param rs the ResultSet
     * @return Product object
     */
    private Product mapResultSetToProduct(ResultSet rs) {
        try {
            return new Product(
                    rs.getString("productId"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("originalPrice"),
                    rs.getDouble("salePrice"),
                    rs.getDouble("priceByUnit"),
                    rs.getDouble("priceByCarton"),
                    rs.getInt("quantity"));
        } catch (SQLException e) {
            logError("Error mapping ResultSet to Product", e);
            return null;
        }
    }
}
