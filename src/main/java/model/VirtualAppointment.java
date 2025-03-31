package model;

import java.time.LocalDateTime;

public class VirtualAppointment extends BaseAppointment implements Remindable {
    private String meetingLink;

    public VirtualAppointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                              String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end,
                              String meetingLink) {
        super(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end);
        this.meetingLink = meetingLink;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    @Override
    public void sendReminder() {
        System.out.println("Reminder: Your virtual appointment (" + getAppointmentTitle() + ") is scheduled at " + getStart() +
                ". Join using this link: " + meetingLink);
    }
}
