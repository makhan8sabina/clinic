package clinic.subsystem;
public class SmsNotifier {

    public void send(String patientName, String message) {
        System.out.println("[SMS] To " + patientName + ": " + message);
    }
}
