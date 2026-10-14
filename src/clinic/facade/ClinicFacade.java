package clinic.facade;

import clinic.model.AppointmentConfirmation;
import clinic.subsystem.Cabinet;
import clinic.subsystem.DoctorSchedule;
import clinic.subsystem.PatientRegistry;
import clinic.subsystem.SmsNotifier;
import clinic.subsystem.SubsystemException;

import java.time.LocalDateTime;

public class ClinicFacade {

    private final PatientRegistry patientRegistry;
    private final DoctorSchedule doctorSchedule;
    private final Cabinet cabinet;
    private final SmsNotifier smsNotifier;

    public ClinicFacade() {
        this(new PatientRegistry(), new DoctorSchedule(), new Cabinet(), new SmsNotifier());
    }

    public ClinicFacade(PatientRegistry patientRegistry, DoctorSchedule doctorSchedule,
                        Cabinet cabinet, SmsNotifier smsNotifier) {
        this.patientRegistry = patientRegistry;
        this.doctorSchedule = doctorSchedule;
        this.cabinet = cabinet;
        this.smsNotifier = smsNotifier;
    }

    public AppointmentConfirmation bookAppointment(String patientName, String doctorName, LocalDateTime time) {
        boolean slotReserved = false;
        try {
            String patientId = patientRegistry.findOrRegister(patientName);
            doctorSchedule.reserveSlot(doctorName, time);
            slotReserved = true;
            String room = cabinet.assignRoom(doctorName, time);
            smsNotifier.send(patientName, "Appointment with %s at %s, room %s".formatted(doctorName, time, room));
            return new AppointmentConfirmation(patientId, doctorName, time, room);
        } catch (SubsystemException e) {
            if (slotReserved) {
                doctorSchedule.releaseSlot(doctorName, time);
            }
            throw new BookingException("Booking failed: " + e.getMessage(), e);
        }
    }
}
