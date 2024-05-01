import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.*;

public class ShoppingCenterGUIController extends JFrame {
    private DefaultTableModel tableModel;
    private JLabel lbl4,lbl5,lbl6,lbl7,lbl8,lbl9;
    static List<Map<String,String>> shoppingCartList = new ArrayList<>();
    ShoppingCart shoppingCart = new ShoppingCart();
    static int tableRowNumber;

    public ShoppingCenterGUIController(){
        List<Product> productList = WestminsterShoppingManager.loadProductList();
        setGUI(productList);
    }


    public void setGUI(List<Product> productList){
        setSize(900, 800);
        setTitle("Westminister Shopping Center");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panel1.add(Box.createVerticalStrut(100));

        JLabel lbl1 = new JLabel("Select Product Category");
        lbl1.setFont(new Font("Arial",Font.PLAIN,20));
        panel1.add(lbl1, BorderLayout.PAGE_START);
        panel1.add(Box.createHorizontalStrut(40));


        String[] options = {"All", "Electronics", "Product.Clothing"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("Arial",Font.PLAIN,16));
        comboBox.setMaximumSize(new Dimension(comboBox.getMaximumSize().width, comboBox.getPreferredSize().height));
        panel1.add(comboBox);
        panel1.add(Box.createHorizontalStrut(250));

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) comboBox.getSelectedItem();
                switch (Objects.requireNonNull(selectedOption)) {
                    case "All":
                        updateTable(tableModel, productList);
                        break;
                    case "Electronics":
                        List<Product> electronicList = sortByCategory(productList, "Electronics");
                        updateTable(tableModel, electronicList);
                        break;
                    case "Product.Clothing":
                        List<Product> clothingList = sortByCategory(productList, "Product.Clothing");
                        updateTable(tableModel, clothingList);
                        break;
                }
            }
        });

        JButton btn1 = new JButton("Shopping Cart");
        btn1.setFont(new Font("", Font.PLAIN,16));
        btn1.setHorizontalAlignment(JButton.CENTER);
        panel1.add(btn1);
        panel1.add(Box.createHorizontalStrut(10));

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShoppingCartGUIController().setVisible(true);
            }
        });

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2.setPreferredSize(new Dimension(900, 100));

        String[] columnNames = {"Product ID", "Name", "Category", "Price ($)", "Info"};
        String[][] data = getCustomizedDataArray(productList);

        tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(35);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    tableRowNumber = table.getSelectedRow();
                    lbl9.setForeground(Color.BLACK);
                    if (tableRowNumber != -1) {
                        String productID = (String) table.getValueAt(tableRowNumber, 0);
                        setSelectedProductDetails(productID, productList);
                    }
                }
            }
        });

        panel2.add(scrollPane,BorderLayout.PAGE_START);
        Border paddingBorder = new EmptyBorder(0, 40, 0, 40);
        panel2.setBorder(paddingBorder);

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(7, 1,0,10));
        panel3.setPreferredSize(new Dimension(900, 250));
        Border paddingBorder2 = new EmptyBorder(0, 40, 30, 10);
        panel3.setBorder(paddingBorder2);


        JLabel lbl2 = new JLabel("Selected Product - Details");
        lbl2.setFont(new Font("Arial",Font.PLAIN,20));
        panel3.add(lbl2, BorderLayout.CENTER);

        lbl4 = new JLabel("Product ID :");
        lbl4.setFont(new Font("Arial",Font.PLAIN,18));
        panel3.add(lbl4);

        lbl5 = new JLabel("Category :");
        lbl5.setFont(new Font("Arial",Font.PLAIN,18));
        panel3.add(lbl5);

        lbl6 = new JLabel("Name : ");
        lbl6.setFont(new Font("Arial",Font.PLAIN,18));
        panel3.add(lbl6);

        lbl7 = new JLabel("Size : ");
        lbl7.setFont(new Font("Arial",Font.PLAIN,18));
        panel3.add(lbl7);

        lbl8 = new JLabel("Colour : ");
        lbl8.setFont(new Font("Arial",Font.PLAIN,18));
        panel3.add(lbl8);

        lbl9 = new JLabel("Items Available : ");
        lbl9.setFont(new Font("Arial",Font.PLAIN,18));
        panel3.add(lbl9);

        JPanel panel4 = new JPanel();
        JButton btn2 = new JButton("Add to Shopping Cart");
        btn2.setFont(new Font("", Font.PLAIN,16));
        btn2.setHorizontalAlignment(JButton.CENTER);
        panel4.add(btn2);

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!lbl4.getText().equals("Product ID :")) {
                    shoppingCart.addItems(lbl4.getText(), productList, lbl5, table, lbl9);
                }else {
                    System.out.println("Choose a item to proceed further!");
                }
            }
        });

        JPanel panel5 = new JPanel();
        panel5.setLayout(new BoxLayout(panel5, BoxLayout.Y_AXIS));
        Border paddingBorder3 = new EmptyBorder(10, 10, 30, 10);
        panel5.setBorder(paddingBorder3);
        panel5.add(panel3);
        panel5.add(panel4);


        add(panel1,BorderLayout.PAGE_START);
        add(panel2,BorderLayout.CENTER);
        add(panel5,BorderLayout.PAGE_END);
    }

    private String[][] getCustomizedDataArray(List<Product> productList) {
        productList.sort(Comparator.comparing(Product::getProductID));
        String[][] data = new String[productList.size()][5];

        int i = 0;
        for (Product product : productList) {

            data[i][0] = product.getProductID();
            data[i][1] = product.getProductName();
            data[i][2] = product.getProductType();
            data[i][3] = String.valueOf(product.getPrice());

            if (product.getProductType().equals("Electronics")) {
                data[i][4] = ((Electronics)product).getBrand()+ ", " + ((Electronics)product).getWarrantyPeriod();
            } else {
                data[i][4] = ((Clothing)product).getSize() + ", " + ((Clothing)product).getColour();
            }
            i++;
        }
        return data;
    }

    public List<Product> sortByCategory(List<Product> productList, String productType){
        List<Product> newList = new ArrayList<>();
        for (Product product:productList) {
            if (product.getProductType().equals(productType)) {
                newList.add(product);
            }
        }
        newList.sort(Comparator.comparing(Product::getProductID));
        return newList;
    }

    public void updateTable (DefaultTableModel table, List<Product> productList){
        String[][] customizedArray = getCustomizedDataArray(productList);
        table.setRowCount(0);
        for (String[] productDataList : customizedArray) {
            table.addRow(productDataList);
        }
    }

    public void setSelectedProductDetails(String productID, List<Product> productList){

        for (Product product : productList) {
            if (product.getProductID().equals(productID)) {
                lbl4.setText("Product ID : " + product.getProductID());
                lbl5.setText("Category : " + product.getProductType());
                lbl6.setText("Name : " + product.getProductName());
                lbl9.setText("Items Available : " + product.getNumAvailable());

                if (product.getProductType().equals("Electronics")) {
                    lbl7.setText("Brand : " + ((Electronics)product).getBrand());
                    lbl8.setText("Warranty Period : " + ((Electronics)product).getWarrantyPeriod());
                } else if (product.getProductType().equals("Product.Clothing")) {
                    lbl7.setText("Size : " + ((Clothing)product).getSize());
                    lbl8.setText("Colour : " + ((Clothing)product).getColour());
                }
            }
        }
    }

}

