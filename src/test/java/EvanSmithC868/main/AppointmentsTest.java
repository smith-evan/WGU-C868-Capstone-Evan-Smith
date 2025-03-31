package EvanSmithC868.main;

import model.Appointments;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class  AppointmentsTest {
    private Appointments appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointments(1, "Grooming Session", "Dog grooming session",
                "New York Salon", "Grooming",
                LocalDateTime.of(2025, 3, 10, 14, 30),
                LocalDateTime.of(2025, 3, 10, 15, 30),
                101, 202, 303);
    }

    @Test
    void testAppointmentCreation() {
        Assertions.assertEquals(1, appointment.getAppointmentID());
        Assertions.assertEquals("Grooming Session", appointment.getAppointmentTitle());
        Assertions.assertEquals("Dog grooming session", appointment.getAppointmentDescription());
        Assertions.assertEquals("New York Salon", appointment.getAppointmentLocation());
        Assertions.assertEquals("Grooming", appointment.getAppointmentType());
        Assertions.assertEquals(101, appointment.getCustomerID());
        Assertions.assertEquals(202, appointment.getUserID());
        Assertions.assertEquals(303, appointment.getContactID());
    }

    @Test
    void testSendReminder() {
        // Capture console output
        appointment.sendReminder();
    }
}
