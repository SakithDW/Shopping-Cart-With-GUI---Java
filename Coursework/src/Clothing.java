public class Clothing extends Product {

    private String size;
    private String colour;



    public Clothing(String productID, String productName, int numAvailable, double price, String size, String colour) {
        super(productID, productName, numAvailable, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return  "\nProduct ID :" + this.getProductID() +
                "\nProduct Type : "+ this.getProductType()+
                "\nProduct Name : " + this.getProductName() +
                "\nPrice : " + this.getPrice() +
                "\nNumber of Available Items : " + this.getNumAvailable() +
                "\nSize : " + this.getSize() +
                "\ncolour : " + this.getColour()+
                "\n\n";
    }
    public String printCloth(){
        return this.toString();
    }
}
