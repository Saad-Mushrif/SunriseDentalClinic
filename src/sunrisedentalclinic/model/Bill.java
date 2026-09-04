package sunrisedentalclinic.model;

public class Bill {

    private int billId;
    private int appointmentNo;
    private double consultationFee;
    private double treatmentCost;
    private double totalAmount;
    private String billDate;

    public Bill() {
    }

    public Bill(int billId, int appointmentNo, double consultationFee,
            double treatmentCost, String billDate) {
        this.billId = billId;
        this.appointmentNo = appointmentNo;
        this.consultationFee = consultationFee;
        this.treatmentCost = treatmentCost;
        this.billDate = billDate;
        this.totalAmount = calculateTotal();
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getAppointmentNo() {
        return appointmentNo;
    }

    public void setAppointmentNo(int appointmentNo) {
        this.appointmentNo = appointmentNo;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getTreatmentCost() {
        return treatmentCost;
    }

    public void setTreatmentCost(double treatmentCost) {
        this.treatmentCost = treatmentCost;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public double calculateTotal() {
        this.totalAmount = this.consultationFee + this.treatmentCost;
        return this.totalAmount;
    }

    public boolean generateReceipt() {
        System.out.println("========== SUNRISE DENTAL CLINIC ==========");
        System.out.println("Bill ID: " + billId);
        System.out.println("Appointment No: " + appointmentNo);
        System.out.println("Date: " + billDate);
        System.out.println("--------------------------------------------");
        System.out.println("Consultation Fee:  " + consultationFee);
        System.out.println("Treatment Cost:    " + treatmentCost);
        System.out.println("--------------------------------------------");
        System.out.println("TOTAL:             " + totalAmount);
        System.out.println("============================================");
        return true;
    }
}
