package sunrisedentalclinic.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import sunrisedentalclinic.model.User;
import sunrisedentalclinic.model.UserSession;
import sunrisedentalclinic.server.SunriseServer; // Reusing the map parser for convenience

public class AuthController {

    public boolean login(String username, String password, boolean rememberMe) {
        try {
            // Format data as form-urlencoded
            String payload = "username=" + URLEncoder.encode(username, "UTF-8") +
                             "&password=" + URLEncoder.encode(password, "UTF-8") +
                             "&rememberMe=" + rememberMe;

            // Send to Web Service
            String responseStr = HttpUtils.sendPostRequest("/api/auth/login", payload);
            
            // Parse response (Reusing SunriseServer's parser for convenience, though we could write a new one)
            Map<String, String> response = SunriseServer.parseFormData(responseStr);

            if ("success".equals(response.get("status"))) {
                // Manually construct the User object from the server's string response
                User loggedInUser = new User();
                loggedInUser.setId(Integer.parseInt(response.get("userId")));
                loggedInUser.setRole(response.get("role"));
                loggedInUser.setName(response.get("name"));
                
                // Save to local Singleton Session
                UserSession session = UserSession.getInstance();
                session.setLoggedInUser(loggedInUser);
                
                // We don't save the token locally here; the UserSession's loginUser method normally does it,
                // but since the server generated it, we just inject it if we wanted to. 
                // For simplicity, we just set the user.
                
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
            return false; // No saved session
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
}
