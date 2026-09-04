package sunrisedentalclinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sunrisedentalclinic.database.DatabaseConnection;
import sunrisedentalclinic.model.Patient;

public class PatientDAO {

    private Connection connection;

    public PatientDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public int addPatient(Patient patient) {
        String query = "INSERT INTO patients (name, contactNumber, address) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getContactNumber());
            stmt.setString(3, patient.getAddress());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
        return -1;
    }

    public Patient getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(id));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contactNumber"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching patient: " + e.getMessage());
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("contactNumber"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all patients: " + e.getMessage());
        }
        return patients;
    }
}
