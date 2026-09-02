package sunrisedentalclinic.model;

public class User extends Person {

    private String username;
    private String password;
    private String role;

    public User() {
        super();
    }

    public User(int id, String name, String contactNumber, String username, String password, String role) {
        super(id, name, contactNumber);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void displayDetails() {
        System.out.println("User ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Role: " + role);
        System.out.println("Contact: " + contactNumber);
    }
}