package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.UserDAO;
import daos.loginAccess.LoginAccesDAO;
import database.PostgreSQLJDBC;
import helpers.CookieHelper;
import helpers.DataFormParser;
import models.users.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.postgresql.jdbc.PgConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class LoginHandler implements HttpHandler {
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;
    private DataFormParser formDataParser;
    private Optional<HttpCookie> cookie;
    private UserDAO userDao;

    public LoginHandler(PostgreSQLJDBC postgreSQLJDBC) {
        this.loginAccesDAO = new LoginAccesDAO(postgreSQLJDBC);
        this.formDataParser = new DataFormParser();
        this.cookieHelper = new CookieHelper();
        this.userDao = new UserDAO(postgreSQLJDBC);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            cookie = cookieHelper.getSessionIdCookie(httpExchange);
            cookie.ifPresent(httpCookie -> loginAccesDAO.deleteSessionID(httpCookie.getValue()));
            response = generatePage();
        }
        if (method.equals("POST")) {

            Map inputs = DataFormParser.getData(httpExchange);
            String providedMail = inputs.get("login").toString();
            String providedPassword = inputs.get("password").toString();
            System.out.println(providedMail);
            System.out.println(providedPassword);

            try {
                User user = userDao.getLoggedUser(providedMail, providedPassword);
                System.out.println(user.getFirstName());
                ObjectMapper mapper = new ObjectMapper();
                response = mapper.writeValueAsString(user);
                sendResponse(response, httpExchange, 200);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                sendResponse("User not authorised", httpExchange, 401);
            }
        }
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


    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(301, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private long hash(String string) {
        long h = 1125899906842597L; // prime
        int len = string.length();
        for (int i = 0; i < len; i++) {
            h = 31 * h + string.charAt(i);
        }
        return h;
    }

    // NOT USED
    private String generatePage() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/login.twig");
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }


    private String redirect(List<Integer> loginData) {
        if (!loginData.isEmpty()) {
            int accessLevel = loginData.get(0);
            if (accessLevel == 1) {
                return "/codecoolerJavaPages/CodecoolerIndex";
            }
            if (accessLevel == 3) {
                return "/adminJavaPages/GreetAdmin";
            }
            if (accessLevel == 2) {
                return "/mentorJavaPages/mentorMainPage";
            }
        }
        return null;
    }
}