package DB;



import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.JDBCType.NULL;

public class DBHelper {
    Connection connection;
    public DBHelper(Connection conn)
    {
        connection=conn;
    }
    public void InsertBranch(String id,String name, String address, String city,boolean isActive, int count) throws SQLException {
   String query="INSERT INTO branch(branchId,name,address,city,isActive,employeeCount)VALUES " +
           "(?,?,?,?,?,?)";
        PreparedStatement ppt= connection.prepareStatement(query);
        ppt.setString(1,id);
        ppt.setString(2,name);
        ppt.setString(3,address);
        ppt.setString(4,city);
        ppt.setBoolean(5,isActive);
        ppt.setInt(6,count);
        ppt.execute();
    }
    public void InsertBranchManager(String name, String email, String password, String branch_id, String manager_id)  {
        String query = "SELECT branchId FROM branch WHERE branchId = ?";
        try {
            // Check if the branch exists
            PreparedStatement ppt = connection.prepareStatement(query);
            ppt.setString(1, branch_id);
            ResultSet rs = ppt.executeQuery();

            // Ensure ResultSet has a result
            if (rs.next()) {
                // The branch exists, proceed with the insert
                String query2 = "INSERT INTO branchmanager(name, email, password, branchCode, managerId) VALUES(?,?,?,?,?)";
                PreparedStatement l1 = connection.prepareStatement(query2);
                l1.setString(1, name);  // Set name
                l1.setString(2, email); // Set email
                l1.setString(3, password); // Set password
                l1.setString(4, branch_id); // Set branch code (branch_id)
                l1.setString(5, manager_id); // Set manager ID
                l1.executeUpdate(); // Execute the insert query
                l1.close();
            } else {
                // Branch ID does not exist
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Branch ID does not exist.");
                alert.show();
            }
// wow
            // Close ResultSet and PreparedStatement
            rs.close();
            ppt.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
