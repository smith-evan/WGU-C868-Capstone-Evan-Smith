package controller;

import DAO.appointmentAccess;
import DAO.userAccess;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;

import java.time.ZoneId;
import java.util.Locale;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Class and methods to verify login, update login log, and set locale/language based on operating system settings.
 */

public class loginView implements Initializable {

    //@FXML private Button cancelButton;
    @FXML private Button loginButton;
    @FXML private TextField loginScreenLocationField;
    @FXML private TextField loginScreenPassword;
    @FXML private TextField loginScreenUsername;
    @FXML private Label passwordField;
    @FXML private Label usernameField;
    @FXML private Label loginField;
    @FXML private Button cancelButtonField;
    @FXML private Label locationText;

    Stage stage;

    /**
     *  Login button method to verify login credentials and log login activity.
     **/

    @FXML
    private void loginButton(ActionEvent event) throws SQLException, IOException, Exception {
        try {
            //definitions for +/- 15 minute appointment check
            ObservableList<Appointments> getAllAppointments = appointmentAccess.getAllAppointments();
            LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
            LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime;
            int getAppointmentID = 0;
            LocalDateTime displayTime = null;
            boolean appointmentWithin15Min = false;

            ResourceBundle rb1 = ResourceBundle.getBundle("languages.login", Locale.getDefault());

            String usernameInput = loginScreenUsername.getText();
            String passwordInput = loginScreenPassword.getText();
            int userId = userAccess.validateUser(usernameInput, passwordInput);

            FileWriter fileWriter = new FileWriter("login_activity.txt", true);
            PrintWriter outputFile = new PrintWriter(fileWriter);

            if (userId > 0) {
                // debugging string to check PATH
                System.out.println(getClass().getResource("/views/MainView.fxml"));

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/MainView.fxml")));
                Scene scene = new Scene(root);
                Stage MainViewReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage = (Stage) loginButton.getScene().getWindow();
                MainViewReturn.setScene(scene);
                MainViewReturn.show();

                //log the successful login
                outputFile.print("user: " + usernameInput + " successfully logged in at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

                //check for upcoming appointments if user is validated
                for (Appointments appointment: getAllAppointments) {
                    startTime = appointment.getStart();
                    if ((startTime.isAfter(currentTimeMinus15Min) || startTime.isEqual(currentTimeMinus15Min)) && (startTime.isBefore(currentTimePlus15Min) || (startTime.isEqual(currentTimePlus15Min)))) {
                        getAppointmentID = appointment.getAppointmentID();
                        displayTime = startTime;
                        appointmentWithin15Min = true;
                    }
                }
                if (appointmentWithin15Min !=false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " + getAppointmentID + " and appointment start time of: " + displayTime);
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("There is an appointment within 15 minutes");
                }

                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("no upcoming appointments");
                }
            } else if (userId < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb1.getString("Error"));
                alert.setContentText(rb1.getString("Incorrect"));
                alert.show();

                //log the failed login attempt
                outputFile.print("user: " + usernameInput + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

            }
            outputFile.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    /**
     * Exit button method to exit the application.
     */

    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Get and use locale for language selection.
     */
    Locale[] suppportedLocales = {
            Locale.ENGLISH,
            Locale.FRENCH
    };
    @Override
    public void initialize(URL url, ResourceBundle rb1) {
        try {
            // Get the system's default zone and locale
            //ZoneId zone = ZoneId.systemDefault();
            //Locale zone = Locale.getDefault();

            //Locale here = getCurrentLocale();
            //rb1 = ResourceBundle.getBundle("Languages/language", here);
            // Get the system's default zone and locale
            //Locale userLocale = Locale.getDefault();

            // Debug information (optional)
            //System.out.println("Default Locale: " + userLocale);
            //System.out.println("Default Zone: " + zone);
            //System.out.println(Locale.getDefault());
            // Set the user's location (use ZoneId for detailed zone information)
            //loginScreenLocationField.setText(zone.toString()); // Displays system's time zone
            //locationText.setText(zone.toString());

            // Load the resource bundle for localization
            //rb1 = ResourceBundle.getBundle("languages/login", Locale.getDefault());

            Locale locale = Locale.getDefault();
            Locale.setDefault(locale);

            ZoneId zone = ZoneId.systemDefault();

            //removed
            //loginScreenLocationField.setText(Locale.getDefault().getDisplayCountry());
            loginScreenLocationField.setText(String.valueOf(zone));

            rb1 = ResourceBundle.getBundle("languages/login", Locale.getDefault());
            // Set localized text in the UI fields
            loginField.setText(rb1.getString("Login"));         // Key: "Login"
            usernameField.setText(rb1.getString("username"));   // Key: "username"
            passwordField.setText(rb1.getString("password"));   // Key: "password"
            loginButton.setText(rb1.getString("Login"));        // Key: "Login"
            cancelButtonField.setText(rb1.getString("Exit"));   // Key: "Exit"
            locationText.setText(rb1.getString("Location"));    // Key: "Location"

        } catch (MissingResourceException e) {
            // Log a specific error if the resource bundle is missing
            System.err.println("Error: Missing resource bundle or keys. Details: " + e.getMessage());
        } catch (Exception e) {
            // Catch other potential errors
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    public static Locale getCurrentLocale() {
        return Locale.getDefault();
    }

}




