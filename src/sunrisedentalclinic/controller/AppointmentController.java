package sunrisedentalclinic.controller;

import java.net.URLEncoder;
import java.util.Map;
import sunrisedentalclinic.server.SunriseServer;

public class AppointmentController {

    public boolean registerAppointment(String patientName, String contactNumber, String address,
            String dentistName, String treatmentType,
            String appointmentDate, String appointmentTime) {
        try {

            String patientPayload = "name=" + URLEncoder.encode(patientName, "UTF-8")
                    + "&contactNumber=" + URLEncoder.encode(contactNumber, "UTF-8")
                    + "&address=" + URLEncoder.encode(address, "UTF-8");

            String patientResponseStr = HttpUtils.sendPostRequest("/api/patients", patientPayload);
            Map<String, String> patientResponse = SunriseServer.parseFormData(patientResponseStr);

            if (!"success".equals(patientResponse.get("status"))) {
                return false;
            }

            String patientId = patientResponse.get("patientId");

            String apptPayload = "patientId=" + URLEncoder.encode(patientId, "UTF-8")
                    + "&dentistName=" + URLEncoder.encode(dentistName, "UTF-8")
                    + "&treatmentType=" + URLEncoder.encode(treatmentType, "UTF-8")
                    + "&appointmentDate=" + URLEncoder.encode(appointmentDate, "UTF-8")
                    + "&appointmentTime=" + URLEncoder.encode(appointmentTime, "UTF-8");

            String apptResponseStr = HttpUtils.sendPostRequest("/api/appointments", apptPayload);
            Map<String, String> apptResponse = SunriseServer.parseFormData(apptResponseStr);

            return "success".equals(apptResponse.get("status"));

        } catch (Exception e) {
            System.out.println("Registration Controller Error: " + e.getMessage());
            return false;
        }
    }

    public Map<String, String> searchAppointment(String appointmentNumber) {
        try {
            String query = "number=" + URLEncoder.encode(appointmentNumber, "UTF-8");
            String responseStr = HttpUtils.sendGetRequest("/api/appointments", query);

            Map<String, String> responseMap = SunriseServer.parseFormData(responseStr);
            return responseMap;

        } catch (Exception e) {
            System.out.println("Search Controller Error: " + e.getMessage());
            return null;
        }
    }
}
