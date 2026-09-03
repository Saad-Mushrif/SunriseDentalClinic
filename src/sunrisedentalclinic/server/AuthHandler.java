package sunrisedentalclinic.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import sunrisedentalclinic.dao.UserDAO;
import sunrisedentalclinic.model.User;
import java.util.UUID;

public class AuthHandler implements HttpHandler {

    private final UserDAO userDAO = new UserDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if ("POST".equalsIgnoreCase(method)) {
            if (path.equals("/api/auth/login")) {
                handleLogin(exchange);
            } else if (path.equals("/api/auth/register")) {
                handleRegister(exchange);
            } else if (path.equals("/api/auth/validate")) {
                handleValidateToken(exchange);
            } else {
                sendResponse(exchange, 404, "Endpoint not found");
            }
        } else {
            sendResponse(exchange, 405, "Method Not Allowed");
        }
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        String formData = getRequestBody(exchange);
        Map<String, String> params = SunriseServer.parseFormData(formData);

        String username = params.get("username");
        String password = params.get("password");
        boolean rememberMe = "true".equalsIgnoreCase(params.get("rememberMe"));

        User user = userDAO.validateUser(username, password);

        if (user != null) {
            String token = "";
            if (rememberMe) {
                token = UUID.randomUUID().toString();
                userDAO.saveSessionToken(user.getId(), token);
            }

            String response = "status=success&userId=" + user.getId()
                    + "&role=" + user.getRole()
                    + "&name=" + user.getName()
                    + "&token=" + token;
            sendResponse(exchange, 200, response);
        } else {
            sendResponse(exchange, 401, "status=error&message=Invalid credentials");
        }
    }

    private void handleRegister(HttpExchange exchange) throws IOException {
        String formData = getRequestBody(exchange);
        Map<String, String> params = SunriseServer.parseFormData(formData);

        User newUser = new User(
                0,
                params.get("name"),
                params.get("contactNumber"),
                params.get("username"),
                params.get("password"),
                params.get("role")
        );

        boolean success = userDAO.addUser(newUser);

        if (success) {
            sendResponse(exchange, 201, "status=success");
        } else {
            sendResponse(exchange, 400, "status=error&message=Registration failed");
        }
    }

    private void handleValidateToken(HttpExchange exchange) throws IOException {
        String formData = getRequestBody(exchange);
        Map<String, String> params = SunriseServer.parseFormData(formData);

        String token = params.get("token");
        User user = userDAO.validateSessionToken(token);

        if (user != null) {
            String response = "status=success&userId=" + user.getId()
                    + "&role=" + user.getRole()
                    + "&name=" + user.getName();
            sendResponse(exchange, 200, response);
        } else {
            sendResponse(exchange, 401, "status=error&message=Invalid token");
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
