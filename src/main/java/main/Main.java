package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * A program for scheduling, and managing appointments and customer contacts.
 */

public class Main extends Application {

    /**
     * JavaDocs are in C195EvanSmith.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Simulate login
        String username = "test";
        String password = "test";  // Correct credentials
        boolean isSuccess = LoginTracker.validateLogin(username, password);
        LoginTracker.logLoginAttempt(username, isSuccess);
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/loginView.fxml"));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/loginView.fxml")));
        //ResourceBundle rb1 = ResourceBundle.getBundle("languages.login", Locale.getDefault());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        //Locale.setDefault(new Locale("fr", "us"));
        //Locale.setDefault(new Locale("fr")); //----Use this hardcode program to french for testing
    }
    /**
     * The main method creates data and starts the application.
     * Runs with JDK 17 and JavaFX 17.0.1 under maven plugin.
     */
    public static void main(String[] args) throws Exception {
        /**
         *  // Set the default time zone to France (Europe/Paris)
         *         TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
         *
         *         // Get the current Locale and ZoneId
         *         Locale zone = Locale.getDefault();
         *         ZoneId franceZone = ZoneId.of("Europe/Paris");
         */
       // evan.c482.JDBC.openConnection();
       // launch(args);
       // evan.c482.JDBC.closeConnection();

       DBConnection.startConnection();
        System.out.println("Establishing database connection...");
        DBConnection.startConnection();
        System.out.println("Database connection established.");

       launch(args);
       DBConnection.closeConnection();
       //System.out.println(Locale.getDefault());
    }
}
