package sunrisedentalclinic.model;

import java.io.*;
import java.util.UUID;

public class UserSession {

    private static UserSession instance;
    private User loggedInUser;
    private String sessionToken;
    private static final String SESSION_FILE = "session.txt";

    // Private constructor for Singleton
    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void loginUser(User user, boolean rememberMe) {
        this.loggedInUser = user;

        if (rememberMe) {
            this.sessionToken = UUID.randomUUID().toString();
            saveTokenLocally(this.sessionToken);
        } else {
            this.sessionToken = null;
            clearLocalToken();
        }
    }

    public void logoutUser() {
        this.loggedInUser = null;
        this.sessionToken = null;
        clearLocalToken();
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public String loadPersistentSession() {
        File file = new File(SESSION_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                this.sessionToken = reader.readLine();
                return this.sessionToken;
            } catch (IOException e) {
                System.out.println("Error reading session file: " + e.getMessage());
            }
        }
        return null;
    }

    private void saveTokenLocally(String token) {
        try (FileWriter writer = new FileWriter(SESSION_FILE)) {
            writer.write(token);
        } catch (IOException e) {
            System.out.println("Error writing session file: " + e.getMessage());
        }
    }

    private void clearLocalToken() {
        File file = new File(SESSION_FILE);
        if (file.exists()) {
            file.delete();
        }
    }
}
