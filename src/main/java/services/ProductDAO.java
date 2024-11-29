package services;

import DB.DBConnection;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
//    public static void main(String[] args) {
//        // Create an instance of ProductDAO
//        ProductDAO productDAO = new ProductDAO();
//
//        // Fetch all products from the database
//        List<Product> products = productDAO.getAllProducts();
//
//        // Display all the products
//        if (products.isEmpty()) {
//            System.out.println("No products found in the database.");
//        } else {
//            System.out.println("List of products from the database:");
//            for (Product product : products) {
//                System.out.println("Product ID: " + product.getProductId());
//                System.out.println("Name: " + product.getName());
//                System.out.println("Category: " + product.getCategory());
//                System.out.println("Original Price: $" + product.getOriginalPrice());
//                System.out.println("Sale Price: $" + product.getSalePrice());
//                System.out.println("Price by Unit: $" + product.getPriceByUnit());
//                System.out.println("Price by Carton: $" + product.getPriceByCarton());
//                System.out.println("Quantity: " + product.getQuantity());
//                System.out.println("-------------------------------------------------");
//            }
//        }
//    }
}
