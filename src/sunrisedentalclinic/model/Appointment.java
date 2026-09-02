package sunrisedentalclinic.model;

public class Appointment {

    private int appointmentNumber;
    private int patientId;
    private String dentistName;
    private String treatmentType;
    private String appointmentDate;
    private String appointmentTime;
    private String status;

    public Appointment() {
    }

    public Appointment(int appointmentNumber, int patientId, String dentistName,
            String treatmentType, String appointmentDate,
            String appointmentTime, String status) {
        this.appointmentNumber = appointmentNumber;
        this.patientId = patientId;
        this.dentistName = dentistName;
        this.treatmentType = treatmentType;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    public int getAppointmentNumber() {
        return appointmentNumber;
    }

    public void setAppointmentNumber(int appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDentistName() {
        return dentistName;
    }

    public void setDentistName(String dentistName) {
        this.dentistName = dentistName;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean schedule() {
        this.status = "Scheduled";
        return true;
    }

    public void displayAppointmentDetails() {
        System.out.println("Appointment No: " + appointmentNumber);
        System.out.println("Patient ID: " + patientId);
        System.out.println("Dentist: " + dentistName);
        System.out.println("Treatment: " + treatmentType);
        System.out.println("Date: " + appointmentDate);
        System.out.println("Time: " + appointmentTime);
        System.out.println("Status: " + status);
    }
}
