package Question7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserBackend {
    private static final String FILE_PATH = "user_data.csv"; // Change this path as per your requirement

    public static void registerUser(String name, String email, String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Append user data to the file
            writer.write(name + "," + email + "," + username + "," + password);
            writer.newLine();
            writer.flush();

            System.out.println("User registered successfully!");
        } catch (IOException e) {
            System.err.println("Error occurred while registering user: " + e.getMessage());
        }
    }

    public static List<String[]> getAllUsers() {
        List<String[]> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                users.add(userData);
            }
        } catch (IOException e) {
            System.err.println("Error occurred while reading user data: " + e.getMessage());
        }

        return users;
    }

    public static boolean authenticateUser(String username, String password) {
        List<String[]> users = getAllUsers();
        for (String[] userData : users) {
            if (userData[2].equals(username) && userData[3].equals(password)) {
                return true; // User found with matching username and password
            }
        }
        return false; // No user found with matching username and password
    }

//    public static String readUsernameFromFile() {
//        String username = null;
//        try (BufferedReader reader = new BufferedReader(new FileReader("current_user.csv"))) {
//            reader.readLine(); // Skip the header
//            username = reader.readLine(); // Read the username
//        } catch (IOException e) {
//            System.err.println("Error reading username from file: " + e.getMessage());
//        }
//        return username;
//    }
}
