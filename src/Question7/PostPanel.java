package Question7;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PostPanel extends JPanel implements ActionListener {
    // Components of the post panel
    private JLabel titleLabel, contentLabel, mediaLabel, hashtagLabel;
    private JTextArea contentArea, hashtagArea;
    private JButton postButton, cancelButton, uploadButton;
    private String username;
    private String selectedMediaPath;

    public PostPanel(String username) {
        this.username = username;
        setLayout(null);

        // Create and add components
        titleLabel = new JLabel("New Post");
        titleLabel.setBounds(180, 10, 100, 30);
        add(titleLabel);

        contentLabel = new JLabel("Content:");
        contentLabel.setBounds(50, 50, 100, 30);
        add(contentLabel);

        contentArea = new JTextArea();
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        contentScrollPane.setBounds(150, 50, 300, 80);
        contentScrollPane.setPreferredSize(new Dimension(300, 80));
        add(contentScrollPane);

        mediaLabel = new JLabel("Media:");
        mediaLabel.setBounds(50, 140, 100, 30);
        add(mediaLabel);

        uploadButton = new JButton("Upload Media");
        uploadButton.setBounds(150, 140, 150, 30);
        uploadButton.addActionListener(this);
        add(uploadButton);

        hashtagLabel = new JLabel("Hashtags:");
        hashtagLabel.setBounds(50, 190, 100, 30);
        add(hashtagLabel);

        hashtagArea = new JTextArea();
        JScrollPane hashtagScrollPane = new JScrollPane(hashtagArea);
        hashtagScrollPane.setBounds(150, 190, 300, 40);
        hashtagScrollPane.setPreferredSize(new Dimension(300, 40));
        add(hashtagScrollPane);

        postButton = new JButton("Post");
        postButton.setBounds(180, 240, 100, 30);
        postButton.addActionListener(this);
        add(postButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(310, 240, 100, 30);
        cancelButton.addActionListener(this);
        add(cancelButton);
    }

    public void actionPerformed(ActionEvent e) {
        // Handle button click events
        if (e.getSource() == postButton) {
            String content = contentArea.getText();
            String hashtags = hashtagArea.getText();

            System.out.println("Posting content: " + content);
            System.out.println("Hashtags: " + hashtags);
            System.out.println("Media path: " + selectedMediaPath);
            System.out.println("Username: " + username); // Display the username

            // Add the content to the database
            ContentBackend.addContent(username, content, hashtags, selectedMediaPath);

            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
        } else if (e.getSource() == cancelButton) {
            // Close the PostPanel
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
        } else if (e.getSource() == uploadButton) {
            // To Open file chooser dialog for selecting media file
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Media Files", "jpg", "jpeg", "png", "gif", "mp4", "avi", "mkv", "mov");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedMediaPath = selectedFile.getAbsolutePath();
                // Display the selected file's name
                contentArea.append(selectedFile.getName() + "\n");
            }
        }
    }

    // Method to read the username from current_user.csv file
    public static String readUsernameFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("current_user.csv"))) {
            reader.readLine(); // Skip the first line (header)
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Error occurred while reading current_user.csv: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        String username = readUsernameFromFile();
        if (username != null) {
            JFrame frame = new JFrame();
            frame.setTitle("Post Panel");
            frame.setSize(550, 350);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(new PostPanel(username), BorderLayout.CENTER);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            System.out.println("Error: Username not found in current_user.csv");
        }
    }
}
