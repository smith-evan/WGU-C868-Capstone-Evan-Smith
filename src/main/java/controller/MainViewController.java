package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main Controller to access other views
 */

public class MainViewController  {

    @FXML private Button mainMenuAppointmentClick;
    @FXML private Button mainMenuCustomerClick;
    @FXML private Button mainMenuExitClick;
    @FXML private Button mainMenuReportsClick;

    /**
     * appointments menu
     */
    @FXML
    void mainMenuAppointmentClick(ActionEvent event) throws IOException {

        Parent appointmentMenu = FXMLLoader.load(getClass().getResource("/views/appointmentView.fxml"));
        Scene scene = new Scene(appointmentMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Goes to the customer main menu
     */
    @FXML
    void mainMenuCustomerClick(ActionEvent event) throws IOException {

        Parent customerMenu = FXMLLoader.load(getClass().getResource("/views/customerView.fxml"));
        Scene scene = new Scene(customerMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Go to reports main menu
     */
    @FXML
    void mainMenuReportsClick(ActionEvent event) throws IOException {

        Parent reportMenu = FXMLLoader.load(getClass().getResource("/views/reports.fxml"));
        Scene scene = new Scene(reportMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * exit button
     */
    public void mainMenuExitClick(ActionEvent ExitButton) {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }

}
