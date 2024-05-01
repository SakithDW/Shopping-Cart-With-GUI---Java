import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingCartGUIController extends JFrame {
    ShoppingCart shoppingCart = new ShoppingCart();
    static List<OrderDetails> orderDetails = getOrderDetails();
    static String filePath = "order.txt";

    ShoppingCartGUIController(){
        List<Map<String, String>> shoppingCartList = ShoppingCenterGUIController.shoppingCartList;
        List<Product> productList = WestminsterShoppingManager.loadProductList();
        setShoppingCartGUI(shoppingCartList, productList);

    }

    private void setShoppingCartGUI(List<Map<String, String>> shoppingCartList, List<Product> productList) {
        setSize(700, 700);
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel1 = new JPanel(new GridLayout(1, 5, 10, 10));

        String[] columnNames = {"Product", "Quantity", "Price"};
        String[][] data = setShoppingCartList(shoppingCartList, productList);

        DefaultTableModel tableModel1 = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel1);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(70);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.PLAIN, 20));


        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 350));
        panel1.add(scrollPane);
        panel1.add(scrollPane, BorderLayout.CENTER);
        Border paddingBorder = new EmptyBorder(10, 40, 0, 40);
        panel1.setBorder(paddingBorder);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(5, 1,0,10));
        Border paddingBorder2 = new EmptyBorder(10, 0, 5, 90);
        panel2.setBorder(paddingBorder2);

        List<Double> totalCalMarks = shoppingCart.calcTotalCost();
        double total = totalCalMarks.get(0);
        double discountFirstPurchase = totalCalMarks.get(1);
        double discountThreeItem = totalCalMarks.get(2);
        double finalTotal = totalCalMarks.get(3);

        JLabel lbl1 = new JLabel("Total  :  "+total);
        lbl1.setFont(new Font("Arial",Font.PLAIN,18));
        lbl1.setHorizontalAlignment(JLabel.RIGHT);
        panel2.add(lbl1);

        JLabel lbl2 = new JLabel("First Purchase Discount (10%)  :  "+discountFirstPurchase);
        lbl2.setFont(new Font("Arial",Font.PLAIN,18));
        lbl2.setHorizontalAlignment(JLabel.RIGHT);
        panel2.add(lbl2);

        JLabel lbl3 = new JLabel("Three items in same Category Discount (20%) :  "+discountThreeItem);
        lbl3.setFont(new Font("Arial",Font.PLAIN,18));
        lbl3.setHorizontalAlignment(JLabel.RIGHT);
        panel2.add(lbl3);
        panel2.add(Box.createVerticalStrut(10));

        JLabel lbl4 = new JLabel("Final Total  :  "+finalTotal);
        lbl4.setFont(new Font("Arial",Font.PLAIN,18));
        lbl4.setHorizontalAlignment(JLabel.RIGHT);
        panel2.add(lbl4);

        JPanel panel3 = new JPanel();
        JButton btn2 = new JButton("Confirm Order");
        btn2.setFont(new Font("", Font.PLAIN,16));
        btn2.setHorizontalAlignment(JButton.CENTER);
        panel3.add(btn2);
        Border paddingBorder1 = new EmptyBorder(10, 10, 10, 10);
        panel3.setBorder(paddingBorder1);
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderDetails newOrderDetails = new OrderDetails(LoginGUIController.user.getUserName(), shoppingCartList, finalTotal);
                orderDetails.add(newOrderDetails);
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                    oos.writeObject(orderDetails);
                    System.out.println("Order Saved Successfully!");
                } catch (IOException en) {
                    System.out.println("Error writing to the file: " + en.getMessage());
                }
            }
        });


        add(panel1, BorderLayout.PAGE_START);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.PAGE_END);

    }

    public static String[][] setShoppingCartList(List<Map<String, String>> shoppingCartList, List<Product> productList){
        String[][] newDateList = new String[shoppingCartList.size()][];
        String product = null;
        int i = 0;

        for (Map<String, String> map : shoppingCartList) {
            for (Product list : productList) {
                if(map.get("Product ID").equals(list.getProductID())){
                    if (map.get("Product Type").equals("Electronics")) {
                        product = list.getProductID() + "\n" + list.getProductName() + "\n" + ((Electronics)list).getBrand() +
                                ", " + ((Electronics)list).getWarrantyPeriod();
                    }else if (map.get("Product Type").equals("Product.Clothing")) {
                        product = list.getProductID() + "\n" + list.getProductName() + "\n" + ((Clothing)list).getSize() +
                                ", " + ((Clothing)list).getColour();
                    }
                }
            }

            newDateList[i] = new String[3];
            newDateList[i][0]= product;
            newDateList[i][1]= map.get("Product Quantity");
            newDateList[i][2]= map.get("Product Price");
            i++;
        }
        return newDateList;
    }


    public static List<OrderDetails> getOrderDetails() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("order.txt"))) {
            orderDetailsList = (List<OrderDetails>) ois.readObject();
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return orderDetailsList;
    }

}
