package services;

import model.Cashier;
import DB.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CashierDAO {

    // Method to add a new Cashier
    public boolean addCashier(Cashier cashier) {
        String query = "INSERT INTO cashier (username, name, email, password, role, branchcode, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, cashier.getUsername());
            pstmt.setString(2, cashier.getName());
            pstmt.setString(3, cashier.getEmail());
            pstmt.setString(4, cashier.getPassword());
            pstmt.setString(5, cashier.getRole());
            pstmt.setString(6, cashier.getBranchCode());
            pstmt.setDouble(7, cashier.getSalary());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing Cashier
    public boolean updateCashier(Cashier cashier) {
        String query = "UPDATE cashier SET name = ?, email = ?, password = ?, role = ?, branchcode = ?, salary = ? WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, cashier.getName());
            pstmt.setString(2, cashier.getEmail());
            pstmt.setString(3, cashier.getPassword());
            pstmt.setString(4, cashier.getRole());
            pstmt.setString(5, cashier.getBranchCode());
            pstmt.setDouble(6, cashier.getSalary());
            pstmt.setString(7, cashier.getUsername());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a Cashier by username
    public boolean deleteCashier(String username) {
        String query = "DELETE FROM cashiers WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch a Cashier by username
    public Cashier getCashier(String username) {
        String query = "SELECT * FROM cashier WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Cashier cashier = new Cashier();
                cashier.setUsername(rs.getString("username"));
                cashier.setName(rs.getString("name"));
                cashier.setEmail(rs.getString("email"));
                cashier.setPassword(rs.getString("password"));
                cashier.setRole(rs.getString("role"));
                cashier.setBranchCode(rs.getString("branchcode"));
                cashier.setSalary(rs.getDouble("salary"));

                return cashier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to fetch all Cashiers
    public List<Cashier> getAllCashiers() {
        List<Cashier> cashiers = new ArrayList<>();
        String query = "SELECT * FROM cashiers";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cashier cashier = new Cashier();
                cashier.setUsername(rs.getString("username"));
                cashier.setName(rs.getString("name"));
                cashier.setEmail(rs.getString("email"));
                cashier.setPassword(rs.getString("password"));
                cashier.setRole(rs.getString("role"));
                cashier.setBranchCode(rs.getString("branchcode"));
                cashier.setSalary(rs.getDouble("salary"));

                cashiers.add(cashier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cashiers;
    }

    public List<Cashier> getAllCashiersByBranchCode(String branchCode) {
        List<Cashier> cashiers = new ArrayList<>();
        String query = "SELECT * FROM cashier WHERE branchcode = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, branchCode);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cashier cashier = new Cashier();
                cashier.setUsername(rs.getString("username"));
                cashier.setName(rs.getString("name"));
                cashier.setEmail(rs.getString("email"));
                cashier.setPassword(rs.getString("password"));
                cashier.setRole(rs.getString("role"));
                cashier.setBranchCode(rs.getString("branchcode"));
                cashier.setSalary(rs.getDouble("salary"));

                cashiers.add(cashier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cashiers;
    }
}


