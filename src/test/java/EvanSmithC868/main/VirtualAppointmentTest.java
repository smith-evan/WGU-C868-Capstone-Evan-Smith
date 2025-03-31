package EvanSmithC868.main;

import model.VirtualAppointment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class VirtualAppointmentTest {
    private VirtualAppointment virtualAppointment;

    @BeforeEach
    void setUp() {
        virtualAppointment = new VirtualAppointment(2, "Online Consultation", "Virtual grooming tips",
                "Zoom", "Virtual Consultation",
                LocalDateTime.of(2025, 3, 11, 16, 0),
                LocalDateTime.of(2025, 3, 11, 17, 0),
                "https://zoom.us/example");
    }

    @Test
    void testVirtualAppointmentCreation() {
        Assertions.assertEquals(2, virtualAppointment.getAppointmentID());
        Assertions.assertEquals("Online Consultation", virtualAppointment.getAppointmentTitle());
        Assertions.assertEquals("Virtual grooming tips", virtualAppointment.getAppointmentDescription());
        Assertions.assertEquals("Zoom", virtualAppointment.getAppointmentLocation());
        Assertions.assertEquals("Virtual Consultation", virtualAppointment.getAppointmentType());
        Assertions.assertEquals("https://zoom.us/example", virtualAppointment.getMeetingLink());
    }

    @Test
    void testSendReminder() {
        virtualAppointment.sendReminder();
    }
}
