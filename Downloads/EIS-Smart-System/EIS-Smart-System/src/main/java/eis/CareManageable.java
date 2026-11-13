package eis;

// Service Layer
interface CareManageable {
    void registerAppointment(Patient patient, Doctor doctor, String date, String diagnosis);

    void showAppointments();
}
