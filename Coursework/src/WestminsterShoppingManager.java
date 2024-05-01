import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private  ArrayList<Product> productList;

    public static void main(String[] args) {
        WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
        westminsterShoppingManager.run(true);
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
    }
    public void loadLists(){
        for(Product product : loadProductList()){
            productList.add(product);
        }
    }

    public void run(boolean programmeOn){
        this.loadLists();
        while (programmeOn){
            this.displayMenu();
            int menuNumber = Validator.intValidatorLimit("Enter the number of task you need to do",0,5);
            switch (menuNumber) {
                case 1 -> this.addProduct();
                case 2 -> this.deleteProduct();
                case 3 -> this.printProducts();
                case 4 -> this.saveToFile();
                case 5 -> {
                    LoginGUIController loginGUIController = new LoginGUIController();
                    loginGUIController.setVisible(true);
                }
                case 0 -> programmeOn=false;
            }
        }
    }

    public static List<Product> loadProductList() {
        List<Product> resultSet = new ArrayList<>();
        try {
            FileInputStream fis =new FileInputStream("data.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            resultSet = (List<Product>) ois.readObject();
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public void addProduct() {

        if (this.productList.size()<=50){
            int type = Validator.intValidatorLimit("Enter the product type(1.clothing/2.electronics): ", 1, 2);
            if (type == 1) {
                String productName = Validator.stringValidator("Enter the name of the clothing item : ",
                        "[A-Za-z]+");
                String productID = generateProductID(false);
                int numAvailable = Validator.intValidator("Enter the number of available items : ");
                double price = Validator.doubleValidator("Enter the price :");
                String size = Validator.stringValidator("Enter the size(XS, S, M, L, XL, XXL)", "^(xs|s|m|l|xl|xxl)$");

                String colour = Validator.stringValidator("Enter the colour : ", "[A-Za-z]+");
                Clothing clothingItem = new Clothing(productID, productName, numAvailable,
                        price, size, colour);
                productList.add(clothingItem);
                saveListToFile(productList,clothingItem.getProductName()+" added to products successfully.");
            }
            if (type == 2) {
                String productName = Validator.stringValidator("Enter the name of the electronic item : ", "[A-Za-z]+");
                String productID = generateProductID(true);
                int numAvailable = Validator.intValidator("Enter the number of available items : ");
                double price = Validator.doubleValidator("Enter the price :");
                String brand = Validator.stringValidator("Enter the brand : ", "[A-Za-z]+");
                int warrantyPeriod = Validator.intValidatorLimit("Enter the warranty period : ", 1, 4);
                Electronics electronicItem = new Electronics(productID, productName, numAvailable,
                        price, brand, warrantyPeriod);
                productList.add(electronicItem);
                saveListToFile(productList, electronicItem.getProductName()+ " added to products successfully ");
            }
        }
    }
    @Override
    public void deleteProduct() {
    System.out.println("Do you want to remove clothing product or electronic product: ");
    int choice = Validator.intValidatorLimit("Enter 1 for clothing and  2 for electronics:", 1, 2);
    if (choice == 1) {
        String productID = Validator.stringValidator("Enter the product ID(C-xxxxx) :", "^C-\\d{5}$");
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product1 = iterator.next();
            if (Objects.equals(product1.getProductID(), productID)) {
                iterator.remove();
                saveListToFile(productList, product1.getProductName() + " removed successfully.");
                break;
            }
        }
    }
    else if (choice == 2) {
        String productID = Validator.stringValidator("Enter the product ID(E-xxxxx) :", "^E-\\d{5}$");
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product1 = iterator.next();
            if (Objects.equals(product1.getProductID(), productID)) {
                iterator.remove();
                saveListToFile(productList, product1.getProductName() + " removed successfully.");
                break;
            }
        }
    }
}

    @Override
    public void printProducts() {
        if(!productList.isEmpty()) {
            productList.sort(Comparator.comparing(Product::getProductID));
            for(Product product: productList) {
                if (product instanceof Clothing clothingProduct) {
                    System.out.println(clothingProduct.printCloth());
                } else if (product instanceof Electronics electronicProduct) {
                    System.out.println(electronicProduct.printElectronics());
                }
            }
        }
        else {
            System.out.println("No items to show.");
        }
    }
    @Override
    public void saveToFile() {
        try {
            FileWriter fileWriter = new FileWriter("products.txt");
            for(Product product: productList){
                if(product instanceof Clothing clothingProduct){
                    fileWriter.append(clothingProduct.printCloth());
                    fileWriter.append("\n");
                }
                else if(product instanceof Electronics electronicProduct){
                    fileWriter.append(electronicProduct.printElectronics());
                    fileWriter.append("\n");
                }
            }
            fileWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    static void saveListToFile(List<Product> productList, String msg) {
        try  {
            FileOutputStream fos = new FileOutputStream("data.txt");
            ObjectOutputStream writer = new ObjectOutputStream(fos);
            writer.writeObject(productList);
            System.out.println(msg);
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }
    public void displayMenu(){
        System.out.print("""
                ____________________________________________
                1) Add Product
                2) Delete Product
                3) Print Info
                4) Save Data To File
                5) Open Customer Portal
                0) Quit
                ____________________________________________
                """);
    }
    public String generateProductID(boolean isElectronic) {
        while (true) {
            String productID = Validator.productIDGenerator(isElectronic);
            boolean exists = false;

            // Check if any product has the same ID
            for (Product product : productList) {
                if (Objects.equals(product.getProductID(), productID)) {
                    exists = true;
                    break;  // Break the loop if a matching product is found
                }
            }

            if (!exists) {
                return productID;
            }
        }
    }
}

