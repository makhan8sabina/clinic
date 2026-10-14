package clinic.model;

import java.time.LocalDateTime;
public record AppointmentConfirmation(String patientId, String doctorName, LocalDateTime time, String cabinet) {
}
