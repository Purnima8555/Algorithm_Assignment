package Question7;

import javax.swing.*;
import java.awt.event.*;

public class UserRegisterPanel extends JFrame implements ActionListener {
    // Components of the registration form
    private JLabel titleLabel, nameLabel, emailLabel, usernameLabel, passwordLabel;
    private JTextField nameField, emailField, usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public UserRegisterPanel() {
        setTitle("User Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create and add components
        titleLabel = new JLabel("User Registration");
        titleLabel.setBounds(150, 10, 200, 30);
        add(titleLabel);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        add(nameField);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 90, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 90, 200, 30);
        add(emailField);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 130, 100, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 130, 200, 30);
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 170, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 170, 200, 30);
        add(passwordField);

        registerButton = new JButton("Register");
        registerButton.setBounds(150, 220, 100, 30);
        registerButton.addActionListener(this);
        add(registerButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Handle button click event
        if (e.getSource() == registerButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Pass user data to backend
            UserDatabase.registerUser(name, email, username, password);

            // Navigate to the login form after successful registration
            JOptionPane.showMessageDialog(this, "Registration successful! Please log in.");
            new UserLoginPanel();
            dispose();
        }
    }

    public static void main(String[] args) {
        new UserRegisterPanel();
    }
}
