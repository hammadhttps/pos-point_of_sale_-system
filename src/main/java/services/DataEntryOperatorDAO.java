package services;

import model.DataEntryOperator;
import DB.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataEntryOperatorDAO {

    // Method to add a new Data Entry Operator
    public boolean addDataEntryOperator(DataEntryOperator operator) {
        String query = "INSERT INTO dataentryoperator (username, name, email, password, role, branchcode, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, operator.getUsername());
            pstmt.setString(2, operator.getName());
            pstmt.setString(3, operator.getEmail());
            pstmt.setString(4, operator.getPassword());
            pstmt.setString(5, operator.getRole());
            pstmt.setString(6, operator.getBranchCode());
            pstmt.setDouble(7, operator.getSalary());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing Data Entry Operator
    public boolean updateDataEntryOperator(DataEntryOperator operator) {
        String query = "UPDATE dataentryoperator SET name = ?, email = ?, password = ?, role = ?, branchcode = ?, salary = ? WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, operator.getName());
            pstmt.setString(2, operator.getEmail());
            pstmt.setString(3, operator.getPassword());
            pstmt.setString(4, operator.getRole());
            pstmt.setString(5, operator.getBranchCode());
            pstmt.setDouble(6, operator.getSalary());
            pstmt.setString(7, operator.getUsername());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a Data Entry Operator by username
    public boolean deleteDataEntryOperator(String username) {
        String query = "DELETE FROM dataentryoperator WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch a Data Entry Operator by username
    public DataEntryOperator getDataEntryOperator(String username) {
        String query = "SELECT * FROM dataentryoperator WHERE username = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                DataEntryOperator operator = new DataEntryOperator();
                operator.setUsername(rs.getString("username"));
                operator.setName(rs.getString("name"));
                operator.setEmail(rs.getString("email"));
                operator.setPassword(rs.getString("password"));
                operator.setRole(rs.getString("role"));
                operator.setBranchCode(rs.getString("branchcode"));
                operator.setSalary(rs.getDouble("salary"));

                return operator;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to fetch all Data Entry Operators
    public List<DataEntryOperator> getAllDataEntryOperators() {
        List<DataEntryOperator> operators = new ArrayList<>();
        String query = "SELECT * FROM dataentryoperator";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                DataEntryOperator operator = new DataEntryOperator();
                operator.setUsername(rs.getString("username"));
                operator.setName(rs.getString("name"));
                operator.setEmail(rs.getString("email"));
                operator.setPassword(rs.getString("password"));
                operator.setRole(rs.getString("role"));
                operator.setBranchCode(rs.getString("branchcode"));
                operator.setSalary(rs.getDouble("salary"));

                operators.add(operator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operators;
    }

    public List<DataEntryOperator> getDataEntryOperatorsByBranchCode(String branchCode) {
        List<DataEntryOperator> operators = new ArrayList<>();
        String query = "SELECT * FROM dataentryoperator WHERE branchcode = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, branchCode);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DataEntryOperator operator = new DataEntryOperator();
                operator.setUsername(rs.getString("username"));
                operator.setName(rs.getString("name"));
                operator.setEmail(rs.getString("email"));
                operator.setPassword(rs.getString("password"));
                operator.setRole(rs.getString("role"));
                operator.setBranchCode(rs.getString("branchcode"));
                operator.setSalary(rs.getDouble("salary"));

                operators.add(operator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operators;
    }

}