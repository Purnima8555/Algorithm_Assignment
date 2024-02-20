package Question7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FriendsPanel extends JPanel implements ActionListener {
    private JLabel titleLabel;
    private JPanel friendsPanel;
    private JButton backButton;

    public FriendsPanel() {
        setLayout(new BorderLayout());

        // Create and add components
        titleLabel = new JLabel("Friends");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        friendsPanel = new JPanel();
        friendsPanel.setLayout(new BoxLayout(friendsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(friendsPanel);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton, BorderLayout.SOUTH);

        loadFriends();
    }

    private void loadFriends() {
        // Retrieve friends from the user_data.csv file
        List<String> friendsList = readFriendsFromFile();

        for (String friend : friendsList) {
            JPanel friendEntry = new JPanel(new BorderLayout());
            JLabel friendLabel = new JLabel(friend);
            JButton addButton = new JButton("Add Friend");
            addButton.addActionListener(this);
            friendEntry.add(friendLabel, BorderLayout.WEST);
            friendEntry.add(addButton, BorderLayout.EAST);
            friendsPanel.add(friendEntry);
        }

        friendsPanel.revalidate();
        friendsPanel.repaint();
    }

    private List<String> readFriendsFromFile() {
        List<String> friendsList = new ArrayList<>();
        String filePath = "user_data.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLineSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                String[] userData = line.split(",");
                String username = userData[0];
                friendsList.add(username);
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading user data: " + e.getMessage());
        }

        return friendsList;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        } else if (e.getActionCommand().equals("Add Friend")) {
            // Handle adding friend
            JButton addButton = (JButton) e.getSource();
            JPanel friendEntry = (JPanel) addButton.getParent();
            JLabel friendLabel = (JLabel) friendEntry.getComponent(0);
            String friendName = friendLabel.getText();
            JOptionPane.showMessageDialog(this, "Add Request Sent to " + friendName);

            // Change the button text to "Add Requested"
            addButton.setText("Requested");

        } else if (e.getActionCommand().equals("Requested")) {
            // Handle reverting friend request
            JButton addButton = (JButton) e.getSource();
            JPanel friendEntry = (JPanel) addButton.getParent();
            JLabel friendLabel = (JLabel) friendEntry.getComponent(0);
            String friendName = friendLabel.getText();
            JOptionPane.showMessageDialog(this, "Friend request to " + friendName + " reverted.");

            // Change the button text back to "Add Friend"
            addButton.setText("Add Friend");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setTitle("Friends Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(350, 300);
            frame.add(new FriendsPanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
