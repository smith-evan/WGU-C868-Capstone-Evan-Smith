package model;

import java.time.LocalDateTime;

// Base class to handle common appointment properties
public abstract class BaseAppointment {
    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentType;
    private LocalDateTime start;
    private LocalDateTime end;

    public BaseAppointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                           String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end) {
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.appointmentLocation = appointmentLocation;
        this.appointmentType = appointmentType;
        this.start = start;
        this.end = end;
    }

    // Encapsulation - getters and setters
    public int getAppointmentID() {
        return appointmentID;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    // Polymorphism - a method that can be overridden by subclasses
  //  public abstract void sendReminder();
}
