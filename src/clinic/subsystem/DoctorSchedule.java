package clinic.subsystem;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DoctorSchedule {

    private static final int WORK_DAY_START_HOUR = 9;
    private static final int WORK_DAY_END_HOUR = 17;

    private final Map<String, Set<LocalDateTime>> bookedSlotsByDoctor = new HashMap<>();

    public DoctorSchedule() {
        List.of("Dr. Akhmetov", "Dr. Serikova", "Dr. Bekov", "Dr. Omarova")
                .forEach(doctor -> bookedSlotsByDoctor.put(doctor, new HashSet<>()));
    }

    public void reserveSlot(String doctorName, LocalDateTime time) {
        Set<LocalDateTime> bookedSlots = bookedSlotsByDoctor.get(doctorName);
        if (bookedSlots == null) {
            throw new SubsystemException("Unknown doctor: " + doctorName);
        }
        if (time.getHour() < WORK_DAY_START_HOUR || time.getHour() >= WORK_DAY_END_HOUR) {
            throw new SubsystemException("Doctors only see patients from 09:00 to 17:00");
        }
        if (!bookedSlots.add(time)) {
            throw new SubsystemException(doctorName + " is already booked at " + time.toLocalTime());
        }
    }

    public void releaseSlot(String doctorName, LocalDateTime time) {
        bookedSlotsByDoctor.get(doctorName).remove(time);
    }
}
