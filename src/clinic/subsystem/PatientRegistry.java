package clinic.subsystem;

import java.util.HashMap;
import java.util.Map;
public class PatientRegistry {

    private final Map<String, String> patientIdByName = new HashMap<>();
    private int sequence = 1;

    public String findOrRegister(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new SubsystemException("Patient name must not be empty");
        }
        return patientIdByName.computeIfAbsent(fullName.trim(), name -> "P-%03d".formatted(sequence++));
    }
}
