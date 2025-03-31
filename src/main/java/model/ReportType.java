package model;

/**
 * Represents a type of report that includes the appointment type and the total number of appointments.
 */
public class ReportType {

    public String appointmentType;

    public int appointmentTotal;

    /**
     * Constructor to initialize the ReportType object with appointment type and total.
     */
    public ReportType(String appointmentType, int appointmentTotal) {
        this.appointmentType = appointmentType;
        this.appointmentTotal = appointmentTotal;
    }

    /**
     * Getter method to retrieve the appointment type.
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     * Getter method to retrieve the total number of appointments.
     */
    public int getAppointmentTotal() {
        return appointmentTotal;
    }
}
