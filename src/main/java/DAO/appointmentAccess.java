package DAO;

import main.DBConnection;
import model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class appointmentAccess {

    /**
     * Observablelist for all appointments in database.
     */


    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> appointmentsObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * from appointments";
        ResultSet rs = null;

        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            rs = ps.executeQuery();

            /**
             * Processes resultset and adds to observablelist.
             */
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments appointment = new Appointments(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
                appointmentsObservableList.add(appointment);
            }
        } catch (SQLException e) {
            // Handle exception
            e.printStackTrace();
            throw e;
        } finally {
            // Make sure ResultSet is closed after use
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return appointmentsObservableList;
    }


    /**
     * Method deletes selected appointment from it's appointment ID.
     */
    public static int deleteAppointment(int customer, Connection connection) throws SQLException {
        String query = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, customer);
        int result = ps.executeUpdate();
        ps.close();
        return result;
    }


}
