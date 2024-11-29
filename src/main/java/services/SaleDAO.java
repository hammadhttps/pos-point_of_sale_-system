package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB.DBConnection;
import model.Product;
import model.Sale;

public class SaleDAO {

    // Create a new sale
    public boolean createSale(Sale sale) {
        String insertSaleQuery = "INSERT INTO Sale (SaleID, TotalAmount, SaleDate) VALUES (?, ?, ?)";
        String insertSaleProductQuery = "INSERT INTO SaleProduct (SaleID, ProductID, Quantity) VALUES (?, ?, ?)";

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            conn.setAutoCommit(false); // Start transaction

            // Insert into Sale table
            try (PreparedStatement saleStmt = conn.prepareStatement(insertSaleQuery)) {
                saleStmt.setString(1, sale.getSaleId());
                saleStmt.setDouble(2, sale.getTotalAmount());
                saleStmt.setDate(3, new Date(sale.getDate().getTime())); // Fixed
                saleStmt.executeUpdate();
            }

            // Insert into SaleProduct table
            try (PreparedStatement saleProductStmt = conn.prepareStatement(insertSaleProductQuery)) {
                for (Product product : sale.getProducts()) {
                    saleProductStmt.setString(1, sale.getSaleId());
                    saleProductStmt.setString(2, product.getProductId());
                    saleProductStmt.setInt(3, product.getQuantity());
                    saleProductStmt.addBatch();
                }
                saleProductStmt.executeBatch();
            }

            conn.commit(); // Commit transaction
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // Rollback transaction on failure
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        }
    }

    // Get a sale by SaleID
    public Sale getSaleById(String saleId) {
        String saleQuery = "SELECT * FROM Sale WHERE SaleID = ?";
        String saleProductQuery = "SELECT p.ProductID, p.ProductName, sp.Quantity FROM SaleProduct sp " +
                "JOIN Product p ON sp.ProductID = p.ProductID WHERE sp.SaleID = ?";

        Connection conn = DBConnection.getInstance().getConnection();

        try {
            // Fetch Sale details
            try (PreparedStatement saleStmt = conn.prepareStatement(saleQuery)) {
                saleStmt.setString(1, saleId);
                ResultSet saleRs = saleStmt.executeQuery();

                if (saleRs.next()) {
                    String id = saleRs.getString("SaleID");
                    double totalAmount = saleRs.getDouble("TotalAmount");
                    Date date = saleRs.getDate("SaleDate");

                    // Fetch products associated with this sale
                    List<Product> products = new ArrayList<>();
                    try (PreparedStatement saleProductStmt = conn.prepareStatement(saleProductQuery)) {
                        saleProductStmt.setString(1, saleId);
                        ResultSet productRs = saleProductStmt.executeQuery();
                        while (productRs.next()) {
                            String productId = productRs.getString("ProductID");
                            String productName = productRs.getString("ProductName");
                            int quantity = productRs.getInt("Quantity");
                            products.add(new Product(productId, productName, quantity)); // Adjust Product constructor
                        }
                    }

                    return new Sale(id, products, totalAmount, date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Delete a sale
    public boolean deleteSale(String saleId) {
        String deleteSaleQuery = "DELETE FROM Sale WHERE SaleID = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(deleteSaleQuery)) {
            stmt.setString(1, saleId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update a sale
    public boolean updateSale(String saleId, double newTotalAmount) {
        String updateSaleQuery = "UPDATE Sale SET TotalAmount = ? WHERE SaleID = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(updateSaleQuery)) {
            stmt.setDouble(1, newTotalAmount);
            stmt.setString(2, saleId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all sales
    public List<Sale> getAllSales() {
        String saleQuery = "SELECT * FROM Sale";
        List<Sale> sales = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(saleQuery);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String saleId = rs.getString("SaleID");
                double totalAmount = rs.getDouble("TotalAmount");
                Date date = rs.getDate("SaleDate");

                sales.add(new Sale(saleId, null, totalAmount,date)); // Add products later if required
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
}
