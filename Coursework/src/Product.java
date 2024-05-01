import java.io.Serializable;

public abstract class Product implements Serializable {

    private String productID;
    private String productName;
    private int numAvailable;
    private double price;

    public Product() {
    }

    public Product(String productID, String name, int numAvailable, double price) {
        this.productID = productID;
        this.productName = name;
        this.numAvailable = numAvailable;
        this.price = price;

    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumAvailable() {
        return numAvailable;
    }

    public void setNumAvailable(int numAvailable) {
        this.numAvailable = numAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductType() {
        if(this instanceof Electronics){
            return "Electronics";
        }else {
            return "Clothing";
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID='" + productID + '\'' +
                ", name='" + productName + '\'' +
                ", productAblNo=" + numAvailable +
                ", price=" + price + '\'' +
                '}';
    }
}
