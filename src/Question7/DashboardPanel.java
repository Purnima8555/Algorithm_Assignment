package Question7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static Question7.PostPanel.readUsernameFromFile;

public class DashboardPanel extends JFrame implements ActionListener {
    // Components of the dashboard
    private JLabel titleLabel, contentLabel;
    private JTextArea contentArea;
    private JButton  friendsButton, postButton, inboxButton, profileButton;

    public DashboardPanel() {
        setTitle("Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("Dashboard");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.NORTH);

        contentLabel = new JLabel("Content:");
        contentLabel.setHorizontalAlignment(SwingConstants.LEFT);
        topPanel.add(contentLabel, BorderLayout.WEST);

        contentArea = new JTextArea();
        contentArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(contentArea);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        friendsButton = new JButton("Friends");
        friendsButton.addActionListener(this);
        bottomPanel.add(friendsButton);

        postButton = new JButton("Post");
        postButton.addActionListener(this);
        bottomPanel.add(postButton);

        inboxButton = new JButton("Inbox");
        inboxButton.addActionListener(this);
        bottomPanel.add(inboxButton);

        profileButton = new JButton("Profile");
        profileButton.addActionListener(this);
        bottomPanel.add(profileButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        // Handle button click events
        if (e.getSource() == friendsButton) {
            JFrame frame = new JFrame("Friends");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(new FriendsPanel());
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
        else if (e.getSource() == postButton) {
            String username = readUsernameFromFile(); // Retrieve the username
            if (username != null) {
                PostPanel postPanel = new PostPanel(username);

                JFrame postFrame = new JFrame("New Post");
                postFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                postFrame.getContentPane().add(postPanel);

                postFrame.setSize(550, 350);
                postFrame.setLocationRelativeTo(null);
                postFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Username not found in current_user.csv", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == inboxButton) {
            // Open InboxPanel
            JFrame frame = new JFrame("Inbox");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(new InboxPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }  else if (e.getSource() == profileButton) {
            JOptionPane.showMessageDialog(this, "Profile not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayContent(String category) {
        List<String[]> contentList = ContentBackend.getContentByCategory(category);
        contentArea.removeAll();

        for (String[] contentData : contentList) {
            String content = contentData[2];
            String mediaPath = contentData[1];

            try {
                ImageIcon imageIcon = new ImageIcon(mediaPath);
                JLabel imageLabel = new JLabel(content, imageIcon, JLabel.LEFT);
                contentArea.add(imageLabel);
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                // If there's an error loading the image, just display the text content
                contentArea.append(content + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardPanel dashboardPanel = new DashboardPanel();
            dashboardPanel.setVisible(true);
            dashboardPanel.setLocationRelativeTo(null);
        });
    }
}
