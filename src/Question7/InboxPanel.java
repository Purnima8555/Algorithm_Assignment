package Question7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class InboxPanel extends JPanel implements ActionListener {
    private JLabel titleLabel;
    private JPanel inboxPanel;
    private JButton backButton;

    public InboxPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300));

        // Create and add components
        titleLabel = new JLabel("Inbox");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        inboxPanel = new JPanel();
        inboxPanel.setLayout(new BoxLayout(inboxPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(inboxPanel);
        add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton, BorderLayout.SOUTH);

        loadInbox();
    }

    private void loadInbox() {
        // Retrieve friend requests and messages from the database
        List<String> friendRequests = ContentBackend.getFriendRequests();
        List<String> messages = ContentBackend.getMessages();

        if (friendRequests.isEmpty()) {
            JLabel noRequestsLabel = new JLabel("No friend requests in the inbox");
            inboxPanel.add(noRequestsLabel);
        } else {
            for (String request : friendRequests) {
                JPanel requestEntry = new JPanel(new BorderLayout());
                JLabel requestLabel = new JLabel("Friend Request: " + request);
                requestEntry.add(requestLabel);
                inboxPanel.add(requestEntry);
            }
        }

        for (String message : messages) {
            JPanel messageEntry = new JPanel(new BorderLayout());
            JLabel messageLabel = new JLabel("Message: " + message);
            messageEntry.add(messageLabel);
            inboxPanel.add(messageEntry);
        }

        inboxPanel.revalidate();
        inboxPanel.repaint();
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        }
    }
}
