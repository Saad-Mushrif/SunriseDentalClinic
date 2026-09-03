package sunrisedentalclinic.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpUtils {

    private static final String BASE_URL = "http://localhost:8080";

    public static String sendPostRequest(String endpoint, String urlEncodedData) {
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Send data
            try (OutputStream os = conn.getOutputStream()) {
                os.write(urlEncodedData.getBytes(StandardCharsets.UTF_8));
            }

            // Read response
            return readResponse(conn);
        } catch (Exception e) {
            System.out.println("HTTP POST Request failed: " + e.getMessage());
            return "status=error&message=Connection Failed";
        }
    }

    public static String sendGetRequest(String endpoint, String queryParams) {
        try {
            URL url = new URL(BASE_URL + endpoint + "?" + queryParams);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read response
            return readResponse(conn);
        } catch (Exception e) {
            System.out.println("HTTP GET Request failed: " + e.getMessage());
            return "status=error&message=Connection Failed";
        }
    }

    private static String readResponse(HttpURLConnection conn) throws Exception {
        int responseCode = conn.getResponseCode();
        InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();
        
        if (is == null) return "status=error&message=No Response";
        
        try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name()).useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
