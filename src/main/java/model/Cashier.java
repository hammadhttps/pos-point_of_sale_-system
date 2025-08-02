package model;

import java.util.Objects;

/**
 * Represents a cashier in the POS system.
 * Contains information about the cashier including personal details,
 * credentials, and work assignment.
 */
public class Cashier {

    // Instance variables
    private String username;
    private String name;
    private String email;
    private String password;
    private String role;
    private String branchCode;
    private double salary;

    // Constants
    private static final double MIN_SALARY = 0.0;
    private static final String DEFAULT_ROLE = "CASHIER";

    /**
     * Default constructor
     */
    public Cashier() {
        this.role = DEFAULT_ROLE;
        this.salary = 0.0;
    }

    /**
     * Constructor with basic cashier information
     * 
     * @param username   unique username for the cashier
     * @param name       full name of the cashier
     * @param email      email address of the cashier
     * @param password   password for the cashier account
     * @param branchCode branch code where cashier works
     */
    public Cashier(String username, String name, String email, String password, String branchCode) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.branchCode = branchCode;
        this.role = DEFAULT_ROLE;
        this.salary = 0.0;
    }

    /**
     * Constructor with all cashier details
     * 
     * @param username   unique username for the cashier
     * @param name       full name of the cashier
     * @param email      email address of the cashier
     * @param password   password for the cashier account
     * @param role       role of the cashier
     * @param branchCode branch code where cashier works
     * @param salary     salary of the cashier
     */
    public Cashier(String username, String name, String email, String password,
            String role, String branchCode, double salary) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role != null ? role : DEFAULT_ROLE;
        this.branchCode = branchCode;
        this.salary = validateSalary(salary);
    }

    // Validation methods
    private double validateSalary(double salary) {
        if (salary < MIN_SALARY) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        return salary;
    }

    // Business logic methods
    /**
     * Check if the cashier has a valid username
     * 
     * @return true if username is not null or empty, false otherwise
     */
    public boolean hasValidUsername() {
        return username != null && !username.trim().isEmpty();
    }

    /**
     * Check if the cashier has a valid email address
     * 
     * @return true if email is not null or empty, false otherwise
     */
    public boolean hasValidEmail() {
        return email != null && !email.trim().isEmpty();
    }

    /**
     * Check if the cashier has a valid password
     * 
     * @return true if password is not null or empty, false otherwise
     */
    public boolean hasValidPassword() {
        return password != null && !password.trim().isEmpty();
    }

    /**
     * Check if the cashier is assigned to a branch
     * 
     * @return true if branch code is not null or empty, false otherwise
     */
    public boolean isAssignedToBranch() {
        return branchCode != null && !branchCode.trim().isEmpty();
    }

    /**
     * Get the display name for the cashier
     * 
     * @return display name or username if name is not available
     */
    public String getDisplayName() {
        if (name != null && !name.trim().isEmpty()) {
            return name.trim();
        }
        return username != null ? username : "Unknown Cashier";
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
     * Check if the cashier has administrative privileges
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

    // Object methods
    @Override
    public String toString() {
        return String.format("Cashier{username='%s', name='%s', role='%s', branch='%s'}",
                username, getDisplayName(), role, branchCode);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Cashier cashier = (Cashier) obj;
        return Objects.equals(username, cashier.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}