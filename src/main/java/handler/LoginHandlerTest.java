package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import daos.UserDAO;
import daos.loginAccess.LoginAccesDAO;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import helpers.CookieHelper;
import helpers.DataFormParser;
import helpers.PassHash;
import models.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.JSONService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;

class LoginHandlerTest {

    private JSONService jsonService;
    private DataFormParser formDataParser;
    private CookieHelper cookieHelper;
    private LoginHandler loginHandler;
    private LoginAccesDAO loginAccesDAO;
    private UserDAO userDAO;
    private PassHash passHash;
    private HttpExchange httpExchange;


    @BeforeEach
    void prepareTest() {
        jsonService = new JSONService();
        DatabaseCredentials credentials = jsonService.readEnviroment();
        PostgreSQLJDBC database = new PostgreSQLJDBC();
        database.connectToDatabase(credentials);
        loginHandler = new LoginHandler(database);
        loginAccesDAO = new LoginAccesDAO(database);
        formDataParser = new DataFormParser();
        cookieHelper = new CookieHelper();
        userDAO = new UserDAO(database);
        passHash = new PassHash();
        httpExchange = Mockito.mock(HttpExchange.class);
    }

    @Test
    void handleTestLoginValid() throws IOException {
        boolean isUserLogged = handleValid(httpExchange);
        Assertions.assertTrue(isUserLogged);
    }

    @Test
    void handleTestLoginInvalid() throws IOException {
        boolean isUserLogged = handleInvalid(httpExchange);
        Assertions.assertFalse(isUserLogged);
    }

    private boolean handleValid(HttpExchange httpExchange) throws IOException {
        String testData = "{\"login\":\"adi09\",\"password\":\"adriano\"}";
        String json = jsonService.parseToString(testData);
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            Optional<HttpCookie> cookie = cookieHelper.getSessionIdCookie(httpExchange);
            cookie.ifPresent(httpCookie -> loginAccesDAO.deleteSessionID(httpCookie.getValue()));
        }

        if (method.equals("POST")) {
            response = json;
            sendResponse(response, httpExchange, 200);
            return true;
        }
        return false;
    }

    private boolean handleInvalid(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {
            String providedMail = "wrong@wrong.pl";
            String providedPassword = "wrong";

            try {
                User user = userDAO.getLoggedUser(providedMail, providedPassword);
                ObjectMapper mapper = new ObjectMapper();
                response = mapper.writeValueAsString(user);
                sendResponse(response, httpExchange, 200);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                sendResponse("User not authorised", httpExchange, 401);
                return false;
            }
        }
        return true;
    }

    private void sendResponse(String response, HttpExchange exchange, int status) throws IOException {
        if (status == 200) {
            exchange.getResponseHeaders().put("Content-type", Collections.singletonList("application/json"));
            exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
        }
        exchange.sendResponseHeaders(status, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}