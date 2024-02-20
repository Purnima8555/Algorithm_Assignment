package Question7;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserLoginPanel extends JFrame implements ActionListener {
    // Components of the login form
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public UserLoginPanel() {
        setTitle("Login");
        setSize(450, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Create and add components
        titleLabel = new JLabel("Login");
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
            boolean isValid = UserBackend.authenticateUser(username, password);

            if (isValid) {
                // Save the username in current_user.csv
                saveCurrentUser(username);
                // Navigate to the dashboard after login
                JOptionPane.showMessageDialog(this, "Logged In Successfully!!");
                DashboardPanel dashboardPanel = new DashboardPanel();
                dashboardPanel.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveCurrentUser(String username) {
        List<String> existingData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("current_user.csv"))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                if (lineCount != 1) { // Skip the second line (previous username)
                    existingData.add(line);
                }
                lineCount++;
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading current_user.csv: " + e.getMessage());
            return;
        }

        // Clear the current_user.csv file before saving the new username
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("current_user.csv"))) {

            for (String line : existingData) {
                writer.write(line);
                writer.newLine();
            }
            // Write the new username
            writer.write(username);
            writer.newLine();
            writer.flush();
            System.out.println("Current user saved: " + username);
        } catch (IOException e) {
            System.err.println("Error occurred while saving current user: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        UserLoginPanel loginPanel = new UserLoginPanel();
        // Center the login panel window on the screen
        loginPanel.setLocationRelativeTo(null);
    }
}
