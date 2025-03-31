package model;

import java.time.LocalDateTime;

public class Appointments extends BaseAppointment implements Remindable {
    private int customerID;
    private int userID;
    private int contactID;

    public Appointments(int appointmentID, String appointmentTitle, String appointmentDescription,
                        String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end,
                        int customerID, int userID, int contactID) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end);
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    // Encapsulation - Getters
    public int getCustomerID() {
        return customerID;
    }

    public int getUserID() {
        return userID;
    }

    public int getContactID() {
        return contactID;
    }
    // Polymorphism
    // Implementing the sendReminder() method from Remindable interface
    @Override
    public void sendReminder() {
        System.out.println("Reminder: Your appointment (" + getAppointmentTitle() + ") is scheduled at " + getStart());
    }
}
