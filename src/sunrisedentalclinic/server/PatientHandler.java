package sunrisedentalclinic.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import sunrisedentalclinic.dao.PatientDAO;
import sunrisedentalclinic.model.Patient;

public class PatientHandler implements HttpHandler {

    private final PatientDAO patientDAO = new PatientDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("POST".equalsIgnoreCase(method)) {
            handleCreatePatient(exchange);
        } else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    private void handleCreatePatient(HttpExchange exchange) throws IOException {
        String formData = getRequestBody(exchange);
        Map<String, String> params = SunriseServer.parseFormData(formData);

        Patient patient = new Patient(
                0,
                params.get("name"),
                params.get("contactNumber"),
                params.get("address")
        );

        int newPatientId = patientDAO.addPatient(patient);

        if (newPatientId != -1) {
            sendResponse(exchange, 201, "status=success&patientId=" + newPatientId);
        } else {
            sendResponse(exchange, 400, "status=error&message=Failed to create patient");
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
