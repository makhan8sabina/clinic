package clinic.client;

import clinic.facade.BookingException;
import clinic.facade.ClinicFacade;
import clinic.model.AppointmentConfirmation;

import java.time.LocalDateTime;

public class Main {

    private static final LocalDateTime TEN_AM = LocalDateTime.of(2026, 10, 20, 10, 0);

    public static void main(String[] args) {
        ClinicFacade clinic = new ClinicFacade();

        System.out.println("--- Scenario 1: successful booking ---");
        book(clinic, "Aidar Nurlanov", "Dr. Akhmetov", TEN_AM);

        System.out.println("\n--- Scenario 2: doctor already booked at that time ---");
        book(clinic, "Dana Sarsen", "Dr. Akhmetov", TEN_AM);

        System.out.println("\n--- Scenario 3: outside working hours ---");
        book(clinic, "Dana Sarsen", "Dr. Serikova", TEN_AM.withHour(20));

        System.out.println("\n--- Scenario 4: unknown doctor ---");
        book(clinic, "Dana Sarsen", "Dr. House", TEN_AM);

        System.out.println("\n--- Scenario 5: no free room (slot is released automatically) ---");
        book(clinic, "Dana Sarsen", "Dr. Serikova", TEN_AM);
        book(clinic, "Timur Aliev", "Dr. Bekov", TEN_AM);
        book(clinic, "Aigerim Kenes", "Dr. Omarova", TEN_AM);
    }

    private static void book(ClinicFacade clinic, String patient, String doctor, LocalDateTime time) {
        try {
            AppointmentConfirmation confirmation = clinic.bookAppointment(patient, doctor, time);
            System.out.println("Done: " + confirmation);
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
    }
}
