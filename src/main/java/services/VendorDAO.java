package services;

import DB.DBConnection;
import model.Vendor;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendorDAO {

    // Fetch all vendors from the database
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        String query = "SELECT vendorId, name, contactInfo FROM vendor";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Vendor vendor = new Vendor();
                vendor.setVendorId(resultSet.getString("vendorId"));
                vendor.setName(resultSet.getString("name"));
                vendor.setContactInfo(resultSet.getString("contactInfo"));

                // Fetch the products for this vendor
                List<Product> products = getProductsByVendorId(vendor.getVendorId());
                vendor.setProductList(products);

                vendors.add(vendor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    // Fetch a single vendor by ID
    public Vendor getVendorById(String vendorId) {
        Vendor vendor = null;
        String query = "SELECT vendorId, name, contactInfo FROM vendor WHERE vendorId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vendorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                vendor = new Vendor();
                vendor.setVendorId(resultSet.getString("vendorId"));
                vendor.setName(resultSet.getString("name"));
                vendor.setContactInfo(resultSet.getString("contactInfo"));

                // Fetch the products for this vendor
                List<Product> products = getProductsByVendorId(vendor.getVendorId());
                vendor.setProductList(products);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendor;
    }

    // Add a new vendor to the database
    public boolean addVendor(Vendor vendor) {
        String query = "INSERT INTO vendor (vendorId, name, contactInfo) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vendor.getVendorId());
            preparedStatement.setString(2, vendor.getName());
            preparedStatement.setString(3, vendor.getContactInfo());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update an existing vendor in the database
    public boolean updateVendor(Vendor vendor) {
        String query = "UPDATE vendor SET name = ?, contactInfo = ? WHERE vendorId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vendor.getName());
            preparedStatement.setString(2, vendor.getContactInfo());
            preparedStatement.setString(3, vendor.getVendorId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete a vendor from the database
    public boolean deleteVendor(String vendorId) {
        String query = "DELETE FROM vendor WHERE vendorId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vendorId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Helper method to fetch products by vendor ID
    private List<Product> getProductsByVendorId(String vendorId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity " +
                       "FROM product WHERE vendorId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vendorId);
            ResultSet resultSet = preparedStatement.executeQuery();

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
}
