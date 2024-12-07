package services;

import DB.DBConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDAO {


    public String addProduct(Product product) {
        String checkQuery = "SELECT COUNT(*) FROM product WHERE productId = ?";
        String insertQuery = "INSERT INTO product (productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean isAdded = false;

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            // Check if the product already exists
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, product.getProductId());
                ResultSet resultSet = checkStatement.executeQuery();

                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    System.out.println("Product already exists in the database.");
                    return "Product already exists, so do not add";
                }
            }

            // Add the product if it does not exist
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, product.getProductId());
                insertStatement.setString(2, product.getName());
                insertStatement.setString(3, product.getCategory());
                insertStatement.setDouble(4, product.getOriginalPrice());
                insertStatement.setDouble(5, product.getSalePrice());
                insertStatement.setDouble(6, product.getPriceByUnit());
                insertStatement.setDouble(7, product.getPriceByCarton());
                insertStatement.setInt(8, product.getQuantity());

                int rowsAffected = insertStatement.executeUpdate();
                isAdded = rowsAffected > 0;

                if (isAdded) {
                    System.out.println("Product added successfully!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Successfully Added in the Inventroy";
    }




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


