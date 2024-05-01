import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUIController extends JFrame {
    static User user = new User();


    public LoginGUIController() {
        setTitle("User Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        Border paddingBorder = new EmptyBorder(20, 20, 0, 20);
        getRootPane().setBorder(paddingBorder);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2,0,20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial",Font.PLAIN,18));
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial",Font.PLAIN,18));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial",Font.PLAIN,18));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial",Font.PLAIN,18));

        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);
        panel.add(new JLabel());

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if(!username.isEmpty() && !password.isEmpty()){
                    user.setUserName(username);
                    user.setPassword(password);
                    new ShoppingCenterGUIController().setVisible(true);
                }else {
                    System.out.println("Please add needed information!");
                }
            }
        });

        add(panel);
        setVisible(true);
    }

}
