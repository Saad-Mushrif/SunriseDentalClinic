package sunrisedentalclinic.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import sunrisedentalclinic.dao.AppointmentDAO;
import sunrisedentalclinic.dao.PatientDAO;
import sunrisedentalclinic.model.Appointment;
import sunrisedentalclinic.model.Patient;

public class AppointmentHandler implements HttpHandler {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final PatientDAO patientDAO = new PatientDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("POST".equalsIgnoreCase(method)) {
            handleCreateAppointment(exchange);
        } else if ("GET".equalsIgnoreCase(method)) {
            handleGetAppointment(exchange);
        } else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    private void handleCreateAppointment(HttpExchange exchange) throws IOException {
        String formData = getRequestBody(exchange);
        Map<String, String> params = SunriseServer.parseFormData(formData);

        try {
            Appointment appt = new Appointment(
                    0,
                    Integer.parseInt(params.get("patientId")),
                    params.get("dentistName"),
                    params.get("treatmentType"),
                    params.get("appointmentDate"),
                    params.get("appointmentTime"),
                    "Scheduled"
            );

            boolean success = appointmentDAO.addAppointment(appt);

            if (success) {
                sendResponse(exchange, 201, "status=success");
            } else {
                sendResponse(exchange, 400, "status=error&message=Failed to schedule appointment");
            }
        } catch (Exception e) {
            sendResponse(exchange, 400, "status=error&message=Invalid parameters");
        }
    }

    private void handleGetAppointment(HttpExchange exchange) throws IOException {

        String query = exchange.getRequestURI().getQuery();
        Map<String, String> params = SunriseServer.parseFormData(query);

        try {
            int appointmentNo = Integer.parseInt(params.get("number"));
            Appointment appt = appointmentDAO.getAppointmentByNumber(appointmentNo);

            if (appt != null) {

                Patient patient = patientDAO.getPatientById(appt.getPatientId());
                String patientName = (patient != null) ? patient.getName() : "Unknown";

                String response = "status=success"
                        + "&patientName=" + patientName
                        + "&dentistName=" + appt.getDentistName()
                        + "&treatmentType=" + appt.getTreatmentType()
                        + "&appointmentDate=" + appt.getAppointmentDate()
                        + "&appointmentTime=" + appt.getAppointmentTime();
                sendResponse(exchange, 200, response);
            } else {
                sendResponse(exchange, 404, "status=error&message=Appointment not found");
            }
        } catch (Exception e) {
            sendResponse(exchange, 400, "status=error&message=Invalid appointment number");
        }
    }

    private String getRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
