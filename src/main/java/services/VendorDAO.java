package services;

import DB.DBConnection;
import model.Vendor;
import model.vendor_product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorDAO {


    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        String vendorQuery = "SELECT vendorId, name, contactInfo FROM vendor";
        String productQuery = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity, vendorId FROM vendor_product";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement vendorStatement = connection.prepareStatement(vendorQuery);
             PreparedStatement productStatement = connection.prepareStatement(productQuery);
             ResultSet vendorResultSet = vendorStatement.executeQuery();
             ResultSet productResultSet = productStatement.executeQuery()) {

            // Group products by vendorId
            Map<String, List<vendor_product>> productMap = new HashMap<>();
            while (productResultSet.next()) {
                vendor_product product = new vendor_product(
                        productResultSet.getString("productId"),
                        productResultSet.getString("name"),
                        productResultSet.getString("category"),
                        productResultSet.getDouble("originalPrice"),
                        productResultSet.getDouble("salePrice"),
                        productResultSet.getDouble("priceByUnit"),
                        productResultSet.getDouble("priceByCarton"),
                        productResultSet.getInt("quantity")
                );

                String vendorId = productResultSet.getString("vendorId");
                productMap.computeIfAbsent(vendorId, k -> new ArrayList<>()).add(product);
            }

            // Build vendor objects
            while (vendorResultSet.next()) {
                Vendor vendor = new Vendor();
                vendor.setVendorId(vendorResultSet.getString("vendorId"));
                vendor.setName(vendorResultSet.getString("name"));
                vendor.setContactInfo(vendorResultSet.getString("contactInfo"));

                // Assign the product list for this vendor
                List<vendor_product> products = productMap.getOrDefault(vendor.getVendorId(), new ArrayList<>());
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
                List<vendor_product> products = getProductsByVendorId(vendor.getVendorId());
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

    // Helper method to fetch vendor_product by vendor ID
    private List<vendor_product> getProductsByVendorId(String vendorId) {
        List<vendor_product> products = new ArrayList<>();
        String query = "SELECT productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity " +
                "FROM vendor_product WHERE vendorId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, vendorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                vendor_product product = new vendor_product(
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

    // Add a product to a vendor's product list
    public boolean addProductToVendor(vendor_product product, String vendorId) {
        String query = "INSERT INTO vendor_product (productId, name, category, originalPrice, salePrice, priceByUnit, priceByCarton, quantity, vendorId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, product.getProductId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getCategory());
            preparedStatement.setDouble(4, product.getOriginalPrice());
            preparedStatement.setDouble(5, product.getSalePrice());
            preparedStatement.setDouble(6, product.getPriceByUnit());
            preparedStatement.setDouble(7, product.getPriceByCarton());
            preparedStatement.setInt(8, product.getQuantity());
            preparedStatement.setString(9, vendorId);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
