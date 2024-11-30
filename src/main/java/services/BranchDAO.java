package services;

import DB.DBConnection;
import model.Branch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {

    // Fetch all branches from the database
    public List<Branch> getAllBranches() {
        List<Branch> branches = new ArrayList<>();
        String query = "SELECT branchId, name, city, address, phone, isActive, employeeCount FROM branch";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Branch branch = new Branch();
                branch.setBranchId(resultSet.getString("branchId"));
                branch.setName(resultSet.getString("name"));
                branch.setCity(resultSet.getString("city"));
                branch.setAddress(resultSet.getString("address"));
                branch.setPhone(resultSet.getString("phone"));
                branch.setActive(resultSet.getBoolean("isActive"));
                branch.setEmployeeCount(resultSet.getInt("employeeCount"));
                branches.add(branch);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branches;
    }

    // Fetch a branch by its ID
    public Branch getBranchById(String branchId) {
        Branch branch = null;
        String query = "SELECT branchId, name, city, address, phone, isActive, employeeCount FROM branch WHERE branchId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, branchId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    branch = new Branch();
                    branch.setBranchId(resultSet.getString("branchId"));
                    branch.setName(resultSet.getString("name"));
                    branch.setCity(resultSet.getString("city"));
                    branch.setAddress(resultSet.getString("address"));
                    branch.setPhone(resultSet.getString("phone"));
                    branch.setActive(resultSet.getBoolean("isActive"));
                    branch.setEmployeeCount(resultSet.getInt("employeeCount"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branch;
    }

    // Add a new branch to the database
    public boolean addBranch(Branch branch) {
        String query = "INSERT INTO branch (branchId, name, city, address, phone, isActive, employeeCount) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, branch.getBranchId());
            preparedStatement.setString(2, branch.getName());
            preparedStatement.setString(3, branch.getCity());
            preparedStatement.setString(4, branch.getAddress());
            preparedStatement.setString(5, branch.getPhone());
            preparedStatement.setBoolean(6, branch.isActive());
            preparedStatement.setInt(7, branch.getEmployeeCount());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update an existing branch
    public boolean updateBranch(Branch branch) {
        String query = "UPDATE branch SET name = ?, city = ?, address = ?, phone = ?, isActive = ?, employeeCount = ? WHERE branchId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, branch.getName());
            preparedStatement.setString(2, branch.getCity());
            preparedStatement.setString(3, branch.getAddress());
            preparedStatement.setString(4, branch.getPhone());
            preparedStatement.setBoolean(5, branch.isActive());
            preparedStatement.setInt(6, branch.getEmployeeCount());
            preparedStatement.setString(7, branch.getBranchId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete a branch by its ID
    public boolean deleteBranch(String branchId) {
        String query = "DELETE FROM branch WHERE branchId = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, branchId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}