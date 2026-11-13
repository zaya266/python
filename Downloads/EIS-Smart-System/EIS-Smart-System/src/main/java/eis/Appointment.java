package eis;

// Data Layer
class Appointment {
    Patient patient;
    Doctor doctor;
    String date;
    String diagnosis;

    Appointment(Patient patient, Doctor doctor, String date, String diagnosis) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.diagnosis = diagnosis;
    }
}
