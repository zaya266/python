package eis;

// Service Layer
class SmartPatientCareService extends PatientCareService {
    String systemLink;

    SmartPatientCareService(String systemLink) {
        this.systemLink = systemLink;
    }

    @Override
    public void registerAppointment(Patient patient, Doctor doctor, String date, String diagnosis) {
        Log.println("Registering appointment via SmartCare System: " + systemLink);
        super.registerAppointment(patient, doctor, date, diagnosis);
    }
}
