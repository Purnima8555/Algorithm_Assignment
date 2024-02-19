package Question7;

import javax.swing.*;
import java.awt.event.*;

public class UserLoginPanel extends JFrame implements ActionListener {
    // Components of the login form
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public UserLoginPanel() {
        setTitle("User Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create and add components
        titleLabel = new JLabel("User Login");
        titleLabel.setBounds(150, 10, 100, 30);
        add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 200, 30);
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 90, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 90, 200, 30);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 130, 100, 30);
        loginButton.addActionListener(this);
        add(loginButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Handle button click event
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Check if username and password are valid
            boolean isValid = UserDatabase.authenticateUser(username, password);

            if (isValid) {
                // Navigate to the dashboard after login
                System.out.println("Login successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new UserLoginPanel();
    }
}
