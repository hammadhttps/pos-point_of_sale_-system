package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a vendor in the POS system.
 * Contains information about the vendor including contact details and
 * associated products.
 */
public class Vendor {

    // Instance variables
    private String vendorId;
    private String name;
    private String contactInfo;
    private List<VendorProduct> productList;

    // Constants
    private static final String DEFAULT_CONTACT_INFO = "Contact information not available";

    /**
     * Default constructor
     */
    public Vendor() {
        this.productList = new ArrayList<>();
    }

    /**
     * Constructor with basic vendor information
     * 
     * @param vendorId    unique identifier for the vendor
     * @param name        vendor name
     * @param contactInfo contact information for the vendor
     */
    public Vendor(String vendorId, String name, String contactInfo) {
        this.vendorId = vendorId;
        this.name = name;
        this.contactInfo = contactInfo != null ? contactInfo : DEFAULT_CONTACT_INFO;
        this.productList = new ArrayList<>();
    }

    /**
     * Constructor with all vendor details
     * 
     * @param vendorId    unique identifier for the vendor
     * @param name        vendor name
     * @param contactInfo contact information for the vendor
     * @param productList list of products supplied by the vendor
     */
    public Vendor(String vendorId, String name, String contactInfo, List<VendorProduct> productList) {
        this.vendorId = vendorId;
        this.name = name;
        this.contactInfo = contactInfo != null ? contactInfo : DEFAULT_CONTACT_INFO;
        this.productList = productList != null ? new ArrayList<>(productList) : new ArrayList<>();
    }

    // Business logic methods
    /**
     * Add a product to the vendor's product list
     * 
     * @param product product to add
     */
    public void addProduct(VendorProduct product) {
        if (product != null && !productList.contains(product)) {
            productList.add(product);
        }
    }

    /**
     * Remove a product from the vendor's product list
     * 
     * @param product product to remove
     * @return true if product was removed, false otherwise
     */
    public boolean removeProduct(VendorProduct product) {
        return productList.remove(product);
    }

    /**
     * Get the number of products supplied by this vendor
     * 
     * @return number of products
     */
    public int getProductCount() {
        return productList.size();
    }

    /**
     * Check if the vendor has any products
     * 
     * @return true if vendor has products, false otherwise
     */
    public boolean hasProducts() {
        return !productList.isEmpty();
    }

    /**
     * Get the total value of all products supplied by this vendor
     * 
     * @return total value of all products
     */
    public double getTotalProductValue() {
        return productList.stream()
                .mapToDouble(VendorProduct::getStockValue)
                .sum();
    }

    /**
     * Get the display name for the vendor
     * 
     * @return display name or vendor ID if name is not available
     */
    public String getDisplayName() {
        if (name != null && !name.trim().isEmpty()) {
            return name.trim();
        }
        return vendorId != null ? vendorId : "Unknown Vendor";
    }

    /**
     * Check if the vendor has valid contact information
     * 
     * @return true if contact info is available, false otherwise
     */
    public boolean hasContactInfo() {
        return contactInfo != null && !contactInfo.trim().isEmpty()
                && !contactInfo.equals(DEFAULT_CONTACT_INFO);
    }

    /**
     * Get products by category
     * 
     * @param category category to filter by
     * @return list of products in the specified category
     */
    public List<VendorProduct> getProductsByCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return productList.stream()
                .filter(product -> category.equalsIgnoreCase(product.getCategory()))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Get low stock products (quantity less than threshold)
     * 
     * @param threshold quantity threshold
     * @return list of products with low stock
     */
    public List<VendorProduct> getLowStockProducts(int threshold) {
        return productList.stream()
                .filter(product -> product.getQuantity() < threshold)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    // Getters and Setters
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo != null ? contactInfo : DEFAULT_CONTACT_INFO;
    }

    public List<VendorProduct> getProductList() {
        return new ArrayList<>(productList); // Return a copy to prevent external modification
    }

    public void setProductList(List<VendorProduct> productList) {
        this.productList = productList != null ? new ArrayList<>(productList) : new ArrayList<>();
    }

    // Object methods
    @Override
    public String toString() {
        return String.format("Vendor{id='%s', name='%s', products=%d}",
                vendorId, getDisplayName(), getProductCount());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vendor vendor = (Vendor) obj;
        return Objects.equals(vendorId, vendor.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorId);
    }
}