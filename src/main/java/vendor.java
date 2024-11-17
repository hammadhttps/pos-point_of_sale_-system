import java.util.List;

public class Vendor {
    private String vendorId;
    private String name;
    private String contactInfo;
    private List<String> productList;

    public Vendor(String vendorId, String name, String contactInfo, List<String> productList) {
        this.vendorId = vendorId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.productList = productList;
    }

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

    public List<String> getProductList() {
        return productList;
    }

    public void setProductList(List<String> productList) {
        this.productList = productList;
    }
}
