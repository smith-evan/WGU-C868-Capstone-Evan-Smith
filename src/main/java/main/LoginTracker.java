package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class LoginTracker {

    public static void logLoginAttempt(String username, boolean isSuccess) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);
        String successMessage = isSuccess ? "Success" : "Failure";
        String logEntry = "Username: " + username + ", Timestamp: " + timestamp + ", Status: " + successMessage;

        File logFile = new File("login_activity.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    // Simulate login validation
    public static boolean validateLogin(String username, String password) {
        return username.equals("user") && password.equals("password");  // Example logic
    }
}
