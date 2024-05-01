import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrderDetails implements Serializable {
    private String customerName;
    private List<Map<String, String>> productDetails;
    private double price;

    public OrderDetails() {
    }

    public OrderDetails(String customerName, List<Map<String, String>> productDetails, double price) {
        this.customerName = customerName;
        this.productDetails = productDetails;
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Map<String, String>> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<Map<String, String>> productDetails) {
        this.productDetails = productDetails;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "customerName='" + customerName + '\'' +
                ", productDetails=" + productDetails +
                ", price=" + price +
                '}';
    }
}

