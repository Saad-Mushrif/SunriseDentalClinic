package sunrisedentalclinic.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import sunrisedentalclinic.dao.BillDAO;
import sunrisedentalclinic.model.Bill;

public class BillingHandler implements HttpHandler {

    private final BillDAO billDAO = new BillDAO();
    private final sunrisedentalclinic.dao.AppointmentDAO appointmentDAO = new sunrisedentalclinic.dao.AppointmentDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("POST".equalsIgnoreCase(method)) {
            handleCreateBill(exchange);
        } else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    private void handleCreateBill(HttpExchange exchange) throws IOException {
        String formData = getRequestBody(exchange);
        Map<String, String> params = SunriseServer.parseFormData(formData);

        try {
            int apptNo = Integer.parseInt(params.get("appointmentNo"));
            double consultFee = Double.parseDouble(params.get("consultationFee"));
            double treatmentCost = Double.parseDouble(params.get("treatmentCost"));
            String billDate = params.get("billDate");

            if (appointmentDAO.getAppointmentByNumber(apptNo) == null) {
                sendResponse(exchange, 400, "status=error&message=Appointment ID does not exist");
                return;
            }

            if (billDAO.getBillByAppointmentNo(apptNo) != null) {
                sendResponse(exchange, 400, "status=error&message=A bill already exists for this Appointment ID");
                return;
            }

            Bill bill = new Bill(0, apptNo, consultFee, treatmentCost, billDate);

            boolean success = billDAO.addBill(bill);

            if (success) {
                String response = "status=success&totalAmount=" + bill.getTotalAmount();
                sendResponse(exchange, 201, response);
            } else {
                sendResponse(exchange, 400, "status=error&message=Failed to save bill");
            }
        } catch (Exception e) {
            sendResponse(exchange, 400, "status=error&message=Invalid billing parameters");
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
