package sunrisedentalclinic.controller;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import sunrisedentalclinic.server.SunriseServer;

public class BillingController {

    public Map<String, String> calculateAndSaveBill(String appointmentNo, String consultationFee, String treatmentCost) {
        try {

            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String payload = "appointmentNo=" + URLEncoder.encode(appointmentNo, "UTF-8")
                    + "&consultationFee=" + URLEncoder.encode(consultationFee, "UTF-8")
                    + "&treatmentCost=" + URLEncoder.encode(treatmentCost, "UTF-8")
                    + "&billDate=" + URLEncoder.encode(today, "UTF-8");

            String responseStr = HttpUtils.sendPostRequest("/api/bills", payload);

            return SunriseServer.parseFormData(responseStr);

        } catch (Exception e) {
            System.out.println("Billing Controller Error: " + e.getMessage());
            return null;
        }
    }
}
