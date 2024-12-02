package services;

import model.BranchManager;
import DB.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchManagerDAO {

    // Method to add a new Branch Manager
    public boolean addBranchManager(BranchManager manager) {
        String query = "INSERT INTO branch_managers (username, name, email, password, role, branch_code, salary, employee_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, manager.getusername());
            pstmt.setString(2, manager.getName());
            pstmt.setString(3, manager.getEmail());
            pstmt.setString(4, manager.getPassword());
            pstmt.setString(5, manager.getRole());
            pstmt.setString(6, manager.getBranchCode());
            pstmt.setDouble(7, manager.getSalary());
            pstmt.setInt(8, manager.getEmployeeCount());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing Branch Manager
    public boolean updateBranchManager(BranchManager manager) {
        String query = "UPDATE branch_managers SET name = ?, email = ?, password = ?, role = ?, branch_code = ?, salary = ?, employee_count = ? WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, manager.getName());
            pstmt.setString(2, manager.getEmail());
            pstmt.setString(3, manager.getPassword());
            pstmt.setString(4, manager.getRole());
            pstmt.setString(5, manager.getBranchCode());
            pstmt.setDouble(6, manager.getSalary());
            pstmt.setInt(7, manager.getEmployeeCount());
            pstmt.setString(8, manager.getusername());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a Branch Manager by username
    public boolean deleteBranchManager(String username) {
        String query = "DELETE FROM branch_managers WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch a Branch Manager by username
    public BranchManager getBranchManager(String username) {
        String query = "SELECT * FROM branch_managers WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                BranchManager manager = new BranchManager();
                manager.setusername(rs.getString("username"));
                manager.setName(rs.getString("name"));
                manager.setEmail(rs.getString("email"));
                manager.setPassword(rs.getString("password"));
                manager.setRole(rs.getString("role"));
                manager.setBranchCode(rs.getString("branch_code"));
                manager.setSalary(rs.getDouble("salary"));
                manager.setEmployeeCount(rs.getInt("employee_count"));

                return manager;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to fetch all Branch Managers
    public List<BranchManager> getAllBranchManagers() {
        List<BranchManager> managers = new ArrayList<>();
        String query = "SELECT * FROM branch_managers";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                BranchManager manager = new BranchManager();
                manager.setusername(rs.getString("username"));
                manager.setName(rs.getString("name"));
                manager.setEmail(rs.getString("email"));
                manager.setPassword(rs.getString("password"));
                manager.setRole(rs.getString("role"));
                manager.setBranchCode(rs.getString("branch_code"));
                manager.setSalary(rs.getDouble("salary"));
                manager.setEmployeeCount(rs.getInt("employee_count"));

                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }
}
