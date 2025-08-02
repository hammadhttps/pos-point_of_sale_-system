package model;

import java.util.Objects;

/**
 * Represents a super administrator in the POS system.
 * Contains information about the super admin including personal details and
 * credentials.
 */
public class SuperAdmin {

    // Instance variables
    private String username;
    private String name;
    private String email;
    private String password;

    // Constants
    private static final String DEFAULT_ROLE = "SUPER_ADMIN";

    /**
     * Default constructor
     */
    public SuperAdmin() {
    }

    /**
     * Constructor with basic super admin information
     * 
     * @param username unique username for the super admin
     * @param name     full name of the super admin
     * @param email    email address of the super admin
     * @param password password for the super admin account
     */
    public SuperAdmin(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Business logic methods
    /**
     * Check if the super admin has a valid username
     * 
     * @return true if username is not null or empty, false otherwise
     */
    public boolean hasValidUsername() {
        return username != null && !username.trim().isEmpty();
    }

    /**
     * Check if the super admin has a valid email address
     * 
     * @return true if email is not null or empty, false otherwise
     */
    public boolean hasValidEmail() {
        return email != null && !email.trim().isEmpty();
    }

    /**
     * Check if the super admin has a valid password
     * 
     * @return true if password is not null or empty, false otherwise
     */
    public boolean hasValidPassword() {
        return password != null && !password.trim().isEmpty();
    }

    /**
     * Get the display name for the super admin
     * 
     * @return display name or username if name is not available
     */
    public String getDisplayName() {
        if (name != null && !name.trim().isEmpty()) {
            return name.trim();
        }
        return username != null ? username : "Unknown Super Admin";
    }

    /**
     * Check if the super admin has full system privileges
     * 
     * @return true (super admin always has full privileges)
     */
    public boolean hasFullPrivileges() {
        return true; // Super admin always has full privileges
    }

    /**
     * Get the role of the super admin
     * 
     * @return role string
     */
    public String getRole() {
        return DEFAULT_ROLE;
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

    // Object methods
    @Override
    public String toString() {
        return String.format("SuperAdmin{username='%s', name='%s', role='%s'}",
                username, getDisplayName(), getRole());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        SuperAdmin that = (SuperAdmin) obj;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
