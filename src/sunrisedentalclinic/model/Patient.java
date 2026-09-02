package sunrisedentalclinic.model;

public class Patient extends Person {

    private String address;

    public Patient() {
        super();
    }

    public Patient(int id, String name, String contactNumber, String address) {
        super(id, name, contactNumber);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void displayDetails() {
        System.out.println("Patient ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Contact: " + contactNumber);
        System.out.println("Address: " + address);
    }
}
