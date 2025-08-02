package model;

import java.util.Objects;

/**
 * Represents a branch location in the POS system.
 * Contains information about the branch including location, contact details,
 * and status.
 */
public class Branch {

    // Instance variables
    private String name;
    private String city;
    private String address;
    private String phone;
    private boolean isActive;
    private int employeeCount;
    private String branchCode;

    // Constants
    private static final int MIN_EMPLOYEE_COUNT = 0;

    /**
     * Default constructor
     */
    public Branch() {
        this.isActive = true;
        this.employeeCount = 0;
    }

    /**
     * Constructor with basic branch information
     * 
     * @param name       branch name
     * @param city       city where branch is located
     * @param address    branch address
     * @param phone      branch phone number
     * @param branchCode unique branch code
     */
    public Branch(String name, String city, String address, String phone, String branchCode) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.branchCode = branchCode;
        this.isActive = true;
        this.employeeCount = 0;
    }

    /**
     * Constructor with all branch details
     * 
     * @param name          branch name
     * @param city          city where branch is located
     * @param address       branch address
     * @param phone         branch phone number
     * @param isActive      whether the branch is active
     * @param employeeCount number of employees at the branch
     * @param branchCode    unique branch code
     */
    public Branch(String name, String city, String address, String phone,
            boolean isActive, int employeeCount, String branchCode) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
        this.employeeCount = validateEmployeeCount(employeeCount);
        this.branchCode = branchCode;
    }

    // Validation methods
    private int validateEmployeeCount(int employeeCount) {
        if (employeeCount < MIN_EMPLOYEE_COUNT) {
            throw new IllegalArgumentException("Employee count cannot be negative");
        }
        return employeeCount;
    }

    // Business logic methods
    /**
     * Check if the branch is operational (active and has employees)
     * 
     * @return true if branch is operational, false otherwise
     */
    public boolean isOperational() {
        return isActive && employeeCount > 0;
    }

    /**
     * Get the full address of the branch
     * 
     * @return formatted full address
     */
    public String getFullAddress() {
        if (address == null && city == null) {
            return "Address not available";
        }

        StringBuilder fullAddress = new StringBuilder();
        if (address != null && !address.trim().isEmpty()) {
            fullAddress.append(address.trim());
        }
        if (city != null && !city.trim().isEmpty()) {
            if (fullAddress.length() > 0) {
                fullAddress.append(", ");
            }
            fullAddress.append(city.trim());
        }

        return fullAddress.toString();
    }

    /**
     * Get a formatted display name for the branch
     * 
     * @return formatted display name
     */
    public String getDisplayName() {
        if (name == null || name.trim().isEmpty()) {
            return "Unnamed Branch";
        }
        return name.trim();
    }

    /**
     * Check if the branch has contact information
     * 
     * @return true if branch has phone number, false otherwise
     */
    public boolean hasContactInfo() {
        return phone != null && !phone.trim().isEmpty();
    }

    /**
     * Get the status description of the branch
     * 
     * @return status description
     */
    public String getStatusDescription() {
        if (!isActive) {
            return "Inactive";
        }
        if (employeeCount == 0) {
            return "No Employees";
        }
        return "Active";
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = validateEmployeeCount(employeeCount);
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    // Object methods
    @Override
    public String toString() {
        return getDisplayName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Branch branch = (Branch) obj;
        return Objects.equals(branchCode, branch.branchCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchCode);
    }
}