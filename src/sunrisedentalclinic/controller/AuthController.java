package sunrisedentalclinic.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import sunrisedentalclinic.model.User;
import sunrisedentalclinic.model.UserSession;
import sunrisedentalclinic.server.SunriseServer;

public class AuthController {

    public boolean login(String username, String password, boolean rememberMe) {
        try {

            String payload = "username=" + URLEncoder.encode(username, "UTF-8")
                    + "&password=" + URLEncoder.encode(password, "UTF-8")
                    + "&rememberMe=" + rememberMe;

            String responseStr = HttpUtils.sendPostRequest("/api/auth/login", payload);

            Map<String, String> response = SunriseServer.parseFormData(responseStr);

            if ("success".equals(response.get("status"))) {

                User loggedInUser = new User();
                loggedInUser.setId(Integer.parseInt(response.get("userId")));
                loggedInUser.setRole(response.get("role"));
                loggedInUser.setName(response.get("name"));

                UserSession session = UserSession.getInstance();
                session.setLoggedInUser(loggedInUser);

                String token = response.get("token");
                if (token != null && !token.isEmpty()) {
                    try (java.io.FileWriter writer = new java.io.FileWriter("session.txt")) {
                        writer.write(token);
                    } catch (Exception ex) {
                        System.out.println("Failed to save local token");
                    }
                }

                return true;
            }
        } catch (Exception e) {
            System.out.println("Login Controller Error: " + e.getMessage());
        }
        return false;
    }

    public boolean validateLocalToken() {
        String localToken = UserSession.getInstance().loadPersistentSession();
        if (localToken == null || localToken.isEmpty()) {
            return false;
        }

        try {
            String payload = "token=" + URLEncoder.encode(localToken, "UTF-8");
            String responseStr = HttpUtils.sendPostRequest("/api/auth/validate", payload);
            Map<String, String> response = SunriseServer.parseFormData(responseStr);

            if ("success".equals(response.get("status"))) {
                User loggedInUser = new User();
                loggedInUser.setId(Integer.parseInt(response.get("userId")));
                loggedInUser.setRole(response.get("role"));
                loggedInUser.setName(response.get("name"));

                UserSession.getInstance().setLoggedInUser(loggedInUser);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Token Validation Error: " + e.getMessage());
        }
        return false;
    }

    public void logout() {
        UserSession.getInstance().logoutUser();
    }

    public boolean registerStaff(String name, String contact, String username, String password, String role) {
        try {
            String payload = "name=" + java.net.URLEncoder.encode(name, "UTF-8")
                    + "&contactNumber=" + java.net.URLEncoder.encode(contact, "UTF-8")
                    + "&username=" + java.net.URLEncoder.encode(username, "UTF-8")
                    + "&password=" + java.net.URLEncoder.encode(password, "UTF-8")
                    + "&role=" + java.net.URLEncoder.encode(role, "UTF-8");

            String responseStr = HttpUtils.sendPostRequest("/api/auth/register", payload);
            java.util.Map<String, String> response = sunrisedentalclinic.server.SunriseServer.parseFormData(responseStr);
            return "success".equals(response.get("status"));
        } catch (Exception e) {
            System.out.println("Registration Error: " + e.getMessage());
            return false;
        }
    }
}
