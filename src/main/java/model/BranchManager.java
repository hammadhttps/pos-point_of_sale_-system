package model;

import java.util.Objects;

/**
 * Represents a branch manager in the POS system.
 * Contains information about the branch manager including personal details,
 * credentials, and management responsibilities.
 */
public class BranchManager {

    // Instance variables
    private String username;
    private String name;
    private String email;
    private String password;
    private String role;
    private String branchCode;
    private double salary;
    private int employeeCount;

    // Constants
    private static final double MIN_SALARY = 0.0;
    private static final int MIN_EMPLOYEE_COUNT = 0;
    private static final String DEFAULT_ROLE = "BRANCH_MANAGER";

    /**
     * Default constructor
     */
    public BranchManager() {
        this.role = DEFAULT_ROLE;
        this.salary = 0.0;
        this.employeeCount = 0;
    }

    /**
     * Constructor with basic manager information
     * 
     * @param username   unique username for the manager
     * @param name       full name of the manager
     * @param email      email address of the manager
     * @param password   password for the manager account
     * @param branchCode branch code where manager works
     */
    public BranchManager(String username, String name, String email, String password, String branchCode) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.branchCode = branchCode;
        this.role = DEFAULT_ROLE;
        this.salary = 0.0;
        this.employeeCount = 0;
    }

    /**
     * Constructor with all manager details
     * 
     * @param username      unique username for the manager
     * @param name          full name of the manager
     * @param email         email address of the manager
     * @param password      password for the manager account
     * @param role          role of the manager
     * @param branchCode    branch code where manager works
     * @param salary        salary of the manager
     * @param employeeCount number of employees managed
     */
    public BranchManager(String username, String name, String email, String password,
            String role, String branchCode, double salary, int employeeCount) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role != null ? role : DEFAULT_ROLE;
        this.branchCode = branchCode;
        this.salary = validateSalary(salary);
        this.employeeCount = validateEmployeeCount(employeeCount);
    }

    // Validation methods
    private double validateSalary(double salary) {
        if (salary < MIN_SALARY) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        return salary;
    }

    private int validateEmployeeCount(int employeeCount) {
        if (employeeCount < MIN_EMPLOYEE_COUNT) {
            throw new IllegalArgumentException("Employee count cannot be negative");
        }
        return employeeCount;
    }

    // Business logic methods
    /**
     * Check if the manager has a valid username
     * 
     * @return true if username is not null or empty, false otherwise
     */
    public boolean hasValidUsername() {
        return username != null && !username.trim().isEmpty();
    }

    /**
     * Check if the manager has a valid email address
     * 
     * @return true if email is not null or empty, false otherwise
     */
    public boolean hasValidEmail() {
        return email != null && !email.trim().isEmpty();
    }

    /**
     * Check if the manager has a valid password
     * 
     * @return true if password is not null or empty, false otherwise
     */
    public boolean hasValidPassword() {
        return password != null && !password.trim().isEmpty();
    }

    /**
     * Check if the manager is assigned to a branch
     * 
     * @return true if branch code is not null or empty, false otherwise
     */
    public boolean isAssignedToBranch() {
        return branchCode != null && !branchCode.trim().isEmpty();
    }

    /**
     * Get the display name for the manager
     * 
     * @return display name or username if name is not available
     */
    public String getDisplayName() {
        if (name != null && !name.trim().isEmpty()) {
            return name.trim();
        }
        return username != null ? username : "Unknown Manager";
    }

    /**
     * Get the formatted salary string
     * 
     * @return formatted salary with currency symbol
     */
    public String getFormattedSalary() {
        return String.format("$%.2f", salary);
    }

    /**
     * Check if the manager has administrative privileges
     * 
     * @return true if role contains "ADMIN" or "MANAGER", false otherwise
     */
    public boolean hasAdminPrivileges() {
        if (role == null) {
            return false;
        }
        String upperRole = role.toUpperCase();
        return upperRole.contains("ADMIN") || upperRole.contains("MANAGER");
    }

    /**
     * Check if the manager has employees under supervision
     * 
     * @return true if employee count > 0, false otherwise
     */
    public boolean hasEmployees() {
        return employeeCount > 0;
    }

    /**
     * Get the management status description
     * 
     * @return status description
     */
    public String getManagementStatus() {
        if (employeeCount == 0) {
            return "No Employees";
        } else if (employeeCount <= 5) {
            return "Small Team";
        } else if (employeeCount <= 15) {
            return "Medium Team";
        } else {
            return "Large Team";
        }
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role != null ? role : DEFAULT_ROLE;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = validateSalary(salary);
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = validateEmployeeCount(employeeCount);
    }

    // Object methods
    @Override
    public String toString() {
        return String.format("BranchManager{username='%s', name='%s', role='%s', branch='%s', employees=%d}",
                username, getDisplayName(), role, branchCode, employeeCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BranchManager that = (BranchManager) obj;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
