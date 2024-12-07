package services;

import DB.DBConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDAO {

    // Fetch all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity FROM product";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("productId"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("originalPrice"),
                        resultSet.getDouble("salePrice"),
                        resultSet.getDouble("priceByUnit"),
                        resultSet.getDouble("priceByCarton"),
                        resultSet.getInt("quantity")
                );
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public boolean updateProductQuantities(Map<Product, Integer> soldProducts) {
        String query = "UPDATE product SET quantity = quantity - ? WHERE productId = ?";
        boolean isUpdated = true;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (Map.Entry<Product, Integer> entry : soldProducts.entrySet()) {
                Product product = entry.getKey();
                int soldQuantity = entry.getValue();

                preparedStatement.setInt(1, soldQuantity); // Decrease the quantity
                preparedStatement.setString(2, product.getProductId()); // Match by productId
                preparedStatement.addBatch(); // Add to batch for execution
            }

            // Execute batch update
            int[] updateCounts = preparedStatement.executeBatch();

            // Check if all updates were successful
            for (int count : updateCounts) {
                if (count <= 0) {
                    isUpdated = false;
                    break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            isUpdated = false;
        }

        return isUpdated;
    }
}


