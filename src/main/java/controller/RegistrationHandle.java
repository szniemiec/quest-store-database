package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.UserDAO;
import daos.codecooler.CodecoolerDAOImpl;
import daos.mentor.MentorDAOImpl;
import database.PostgreSQLJDBC;
import enums.RoleEnum;
import helpers.Parser;
import models.users.AccountCredentials;
import models.users.Codecooler;
import models.users.Mentor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class RegistrationHandle implements HttpHandler {
    MentorDAOImpl mentorDao;
    private AccountCredentials accountCredentials;
    private UserDAO userDAO;
    PostgreSQLJDBC postgreSQLJDBC;
    CodecoolerDAOImpl codecoolerDAO;

    public RegistrationHandle(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
        this.accountCredentials = new AccountCredentials();
        this.userDAO = new UserDAO(postgreSQLJDBC);
        this.codecoolerDAO = new CodecoolerDAOImpl(postgreSQLJDBC);
        this.mentorDao = new MentorDAOImpl(postgreSQLJDBC);

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        String method = exchange.getRequestMethod(); //post or get string
        System.out.println(method);
        exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
        exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
// wysyłanie inputów ze strony
        if (method.equals("POST")) {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String form = bufferedReader.readLine();
            Map<String, String> data = Parser.parseFormData(form);
            System.out.println(form);
            System.out.println(data);

            accountCredentials.setLogin(data.get("login"))
                    .setPassword(data.get("password"))
                    .setEmail(data.get("email"))
                    .setRoleEnum(RoleEnum.MENTOR);

            String firstName = data.get("firstName");
            String lastName = data.get("lastName");

            Mentor mentor = new Mentor(accountCredentials, firstName, lastName);
            mentor.setFirstName(firstName)
                    .setLastName(lastName)
                    .setAccountCredentials(accountCredentials);
            try {

                mentorDao.setMentor(mentor, accountCredentials);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
