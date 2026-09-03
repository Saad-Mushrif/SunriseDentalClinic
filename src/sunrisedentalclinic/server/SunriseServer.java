package sunrisedentalclinic.server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class SunriseServer {

    private HttpServer server;
    private static final int PORT = 8080;

    public void startServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);

            server.createContext("/api/auth", new AuthHandler());
            server.createContext("/api/patients", new PatientHandler());
            server.createContext("/api/appointments", new AppointmentHandler());
            server.createContext("/api/bills", new BillingHandler());

            server.setExecutor(null);
            server.start();
            System.out.println("Sunrise Dental Clinic REST API Server started on port " + PORT);
        } catch (IOException e) {
            System.out.println("Failed to start server: " + e.getMessage());
        }
    }

    public void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped.");
        }
    }

    public static Map<String, String> parseFormData(String formData) {
        Map<String, String> map = new HashMap<>();
        if (formData == null || formData.isEmpty()) {
            return map;
        }

        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            try {
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], "UTF-8") : "";
                map.put(key, value);
            } catch (Exception e) {
                System.out.println("Error decoding form data: " + e.getMessage());
            }
        }
        return map;
    }
}
