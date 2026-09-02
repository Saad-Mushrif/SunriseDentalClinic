package sunrisedentalclinic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sunrisedentalclinic.database.DatabaseConnection;
import sunrisedentalclinic.model.Bill;

public class BillDAO {

    private Connection connection;

    public BillDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean addBill(Bill bill) {
        String query = "INSERT INTO bills (appointmentNo, consultationFee, treatmentCost, totalAmount, billDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bill.getAppointmentNo());
            stmt.setDouble(2, bill.getConsultationFee());
            stmt.setDouble(3, bill.getTreatmentCost());
            stmt.setDouble(4, bill.getTotalAmount());
            stmt.setString(5, bill.getBillDate());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding bill: " + e.getMessage());
            return false;
        }
    }

    public Bill getBillByAppointmentNo(int appointmentNo) {
        String query = "SELECT * FROM bills WHERE appointmentNo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, appointmentNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Bill(
                        rs.getInt("billId"),
                        rs.getInt("appointmentNo"),
                        rs.getDouble("consultationFee"),
                        rs.getDouble("treatmentCost"),
                        rs.getString("billDate")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching bill: " + e.getMessage());
        }
        return null;
    }
}
