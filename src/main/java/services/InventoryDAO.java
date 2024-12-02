/*
package services;

import model.Product;
import model.Inventory;
import DB.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    private final Connection connection;

    public InventoryDAO() {
        this.connection = DBConnection.getInstance().getConnection();
    }

    // Method to fetch all products from the database
    public Inventory getAllProducts() {
        Inventory inventory = new Inventory();

        String query = "SELECT * FROM products";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
              Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setQuantity(rs.getInt("quantity"));
                inventory.addProduct(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    // Method to fetch a product by its ID
    public Product getProductById(String productId) {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getString("product_id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setQuantity(rs.getInt("quantity"));
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to add a new product to the database
    public boolean addProduct(Product product) {
        String query = "INSERT INTO products (product_id, name, category, quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, product.getProductId());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setInt(4, product.getQuantity());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update an existing product
    public boolean updateProduct(Product product) {
        String query = "UPDATE products SET name = ?, category = ?, quantity = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, product.getName());
            pstmt.setString(2, product.getCategory());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setString(4, product.getProductId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete a product from the database
    public boolean deleteProduct(String productId) {
        String query = "DELETE FROM products WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, productId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update the quantity of a product
    public boolean updateProductQuantity(String productId, int quantity) {
        String query = "UPDATE products SET quantity = ? WHERE product_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, productId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
*/
