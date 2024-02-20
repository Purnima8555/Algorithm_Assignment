package Question7;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContentBackend {
    private static final String FILE_PATH = "content_data.csv";

    public static void addContent(String username, String content, String hashtags, String mediaPath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + "," + mediaPath + "," + "\"" + content + "\"" + "," + hashtags );
            writer.newLine();
            writer.flush();

            System.out.println("Content added successfully!");
        } catch (IOException e) {
            System.err.println("Error occurred while adding content: " + e.getMessage());
        }
    }

    public static List<String[]> getContentByUsername(String username) {
        List<String[]> userContent = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contentData = line.split(",");
                String contentUsername = contentData[0];
                if (contentUsername.equals(username)) {
                    userContent.add(contentData);
                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading content data: " + e.getMessage());
        }

        return userContent;
    }

    public static List<String[]> getContentByCategory(String category) {
        List<String[]> categoryContent = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contentData = line.split(",");
                String contentCategory = contentData[2]; // Assuming category is in the third column
                if (contentCategory.equalsIgnoreCase(category)) {
                    categoryContent.add(contentData);
                }
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading content data: " + e.getMessage());
        }

        return categoryContent;
    }

    public static List<String> getFriendRequests() {
        // Simulate retrieving friend requests from the database
        List<String> friendRequests = new ArrayList<>();
        // Add sample friend requests
        friendRequests.add("John");
        friendRequests.add("Alice");
        friendRequests.add("Bob");
        return friendRequests;
    }

    public static List<String> getMessages() {
        // Simulate retrieving messages from the database
        List<String> messages = new ArrayList<>();
        // Add sample messages
        messages.add("No Messages Yet!");
        return messages;
    }
}
