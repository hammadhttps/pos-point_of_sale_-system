package model;

import java.util.List;

public class Vendor {
    private String vendorId;
    private String name;
    private String contactInfo;
    private List<vendor_product> productList;

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
        this.contactInfo = contactInfo;
    }

    public List<vendor_product> getProductList() {
        return productList;
    }

    public void setProductList(List<vendor_product> productList) {
        this.productList = productList;
    }
}