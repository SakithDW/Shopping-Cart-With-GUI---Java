public class Electronics extends Product{

    private String brand;
    private int warrantyPeriod;

    public Electronics() {
    }

    public Electronics(String brand, int warrantyPeriod) {
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public Electronics(String productID, String productName, int numAvailable, double price, String brand, int warrantyPeriod) {
        super(productID, productName, numAvailable, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return  "Product ID : " + this.getProductID() +
                "\nProduct Type : "+ this.getProductType()+
                "\nProduct Name : " + this.getProductName() +
                "\nPrice : " + this.getPrice() +
                "\nNumber of Available Items : " + this.getNumAvailable() +
                "\nBrand : " + this.getBrand() +
                "\nWarranty Period : " + this.getWarrantyPeriod()+
                "\n\n";
    }
    public String printElectronics(){
        return this.toString();
    }
}
