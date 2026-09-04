package sunrisedentalclinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sunrisedentalclinic.database.DatabaseConnection;
import sunrisedentalclinic.model.Appointment;

public class AppointmentDAO {

    private Connection connection;

    public AppointmentDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean addAppointment(Appointment appointment) {
        String query = "INSERT INTO appointments (patientId, dentistName, treatmentType, appointmentDate, appointmentTime, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, appointment.getPatientId());
            stmt.setString(2, appointment.getDentistName());
            stmt.setString(3, appointment.getTreatmentType());
            stmt.setString(4, appointment.getAppointmentDate());
            stmt.setString(5, appointment.getAppointmentTime());
            stmt.setString(6, appointment.getStatus());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding appointment: " + e.getMessage());
            return false;
        }
    }

    public Appointment getAppointmentByNumber(int appointmentNumber) {
        String query = "SELECT * FROM appointments WHERE appointmentNumber = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, appointmentNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Appointment(
                        rs.getInt("appointmentNumber"),
                        rs.getInt("patientId"),
                        rs.getString("dentistName"),
                        rs.getString("treatmentType"),
                        rs.getString("appointmentDate"),
                        rs.getString("appointmentTime"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching appointment: " + e.getMessage());
        }
        return null;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("appointmentNumber"),
                        rs.getInt("patientId"),
                        rs.getString("dentistName"),
                        rs.getString("treatmentType"),
                        rs.getString("appointmentDate"),
                        rs.getString("appointmentTime"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all appointments: " + e.getMessage());
        }
        return appointments;
    }
}
