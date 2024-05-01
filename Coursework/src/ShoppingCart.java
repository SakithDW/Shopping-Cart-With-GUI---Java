import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.*;

public class ShoppingCart extends DefaultTableCellRenderer {

    private List<Product> productList;

    public ShoppingCart() {
    }

    public ShoppingCart(List<Product> productList) {
        this.productList = productList;
    }


    public List<Product> getProductList() {
        return productList;
    }


    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void addItems(String productID, List<Product> productList, JLabel lbl5, JTable table, JLabel lbl9) {

        String[] id = (productID.split("Product ID : "));
        String[] category = (lbl5.getText().split("Category : "));
        Map<String, String> list = new HashMap<>();


        for (Product product : productList) {
            if (product.getNumAvailable() <= 0) {
                lbl9.setText("Items Available : No Available Items!");
                lbl9.setForeground(Color.RED);
            } else {
                if (product.getProductID().equals(id[1])) {
                    if (product.getNumAvailable() < 3) {
                        lbl9.setForeground(Color.RED);
                        CustomRenderer centerRenderer = new CustomRenderer(ShoppingCenterGUIController.tableRowNumber);
                        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);

                        for (int i = 0; i < table.getColumnCount(); i++) {
                            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                        }
                    }
                    lbl9.setForeground(Color.BLACK);
                    product.setNumAvailable(product.getNumAvailable() - 1);
                    WestminsterShoppingManager.saveListToFile(productList, product.getProductName() + " availability : " + product.getNumAvailable());
                    lbl9.setText("Items Available : " + product.getNumAvailable());

                    list.put("Product ID", id[1]);
                    list.put("Product Quantity", String.valueOf(1));
                    list.put("Product Price", String.valueOf(product.getPrice()));
                    list.put("Product Type", category[1]);
                    Iterator<Map<String, String>> iterator = ShoppingCenterGUIController.shoppingCartList.iterator();
                    while (iterator.hasNext()) {
                        Map<String, String> dataList = iterator.next();
                        if (product.getProductID().equals(dataList.get("Product ID"))) {
                            iterator.remove();
                            list.put("Product Quantity", String.valueOf(Integer.parseInt(dataList.get("Product Quantity")) + 1));
                            double currentPrice = Double.parseDouble(dataList.get("Product Price"));
                            double additionalPrice = product.getPrice();
                            double newPrice = currentPrice + additionalPrice;
                            list.put("Product Price", String.valueOf(Math.round(newPrice * 100.0) / 100.0));
                        }
                    }
                }
            }
        }
        ShoppingCenterGUIController.shoppingCartList.add(list);
    }



    public List<Double> calcTotalCost(){
        List<Double> totalCal = new ArrayList<>();

        double total = 0;
        for (Map<String, String> map : ShoppingCenterGUIController.shoppingCartList) {
            total+=Double.parseDouble(map.get("Product Price"));
        }

        double discountThreePurchase = 0;
        int electronicCount = 0;
        int clothingCount = 0;

        for (Map<String, String> map : ShoppingCenterGUIController.shoppingCartList) {
            if(map.get("Product Type").equals("Electronics")){
                if(map.get("Product Quantity").compareTo("1") > 0){
                    electronicCount+=Integer.parseInt(map.get("Product Quantity"));
                }else {
                    electronicCount++;
                }
            }else if(map.get("Product Type").equals("Product.Clothing")){
                if(map.get("Product Quantity").compareTo("1") > 0){
                    clothingCount+=Integer.parseInt(map.get("Product Quantity"));
                }else {
                    clothingCount++;
                }
            }
        }
        if(electronicCount>=3 || clothingCount>=3){
            discountThreePurchase = (20 / 100.0) * total;
        }

        double discountFirstPurchase = 0;
        int count = 0;
        if (ShoppingCartGUIController.orderDetails.isEmpty()){discountFirstPurchase = (10 / 100.0) * total;}
        else {
            for (OrderDetails orderList: ShoppingCartGUIController.orderDetails) {
                if (orderList.getCustomerName() == null || !orderList.getCustomerName().equals(LoginGUIController.user.getUserName())) {
                    count++;
                }
            }
            if (count==ShoppingCartGUIController.orderDetails.size()){
                discountFirstPurchase = (10 / 100.0) * total;
            }
        }

        double finalTotal = total-(discountFirstPurchase+discountThreePurchase);

        totalCal.add(Math.round(total * 100.0) / 100.0);
        totalCal.add(Math.round(discountFirstPurchase * 100.0) / 100.0);
        totalCal.add(Math.round(discountThreePurchase * 100.0) / 100.0);
        totalCal.add(Math.round(finalTotal * 100.0) / 100.0);

        return totalCal;

    }

    private static class CustomRenderer extends DefaultTableCellRenderer {
        private final int targetRow;

        public CustomRenderer(int targetRow) {
            this.targetRow = targetRow;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row == targetRow) {
                rendererComponent.setBackground(Color.RED);
                rendererComponent.setForeground(Color.WHITE);
            } else {
                rendererComponent.setBackground(table.getBackground());
                rendererComponent.setForeground(table.getForeground());
            }

            return rendererComponent;
        }
    }
}
