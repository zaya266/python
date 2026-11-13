package eis;

import java.util.ArrayList;
import java.util.List;

// Service Layer
class PatientCareService implements CareManageable {
    List<Appointment> appointments = new ArrayList<>();

    @Override
    public void registerAppointment(Patient patient, Doctor doctor, String date, String diagnosis) {
        appointments.add(new Appointment(patient, doctor, date, diagnosis));
        Log.println("Appointment registered: " + patient.name + " with Dr. " + doctor.name);
    }

    @Override
    public void showAppointments() {
        Log.println("\nAll Appointments:");
        if (appointments.isEmpty()) {
            Log.println("No appointments yet.");
        }
        for (Appointment a : appointments) {
            Log.println("- " + a.date + " | " + a.patient.name + " → Dr. "
                    + a.doctor.name + " (" + a.doctor.specialization + ")"
                    + " | " + a.diagnosis);
        }
    }
}
