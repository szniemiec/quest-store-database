package controller;

import com.fasterxml.jackson.databind.node.POJONode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.UserDAO;
import daos.codecooler.CodecoolerDAOImpl;
import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import helpers.Parser;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Codecooler;
import models.users.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class RegistrationHandle implements HttpHandler {
    private AccountCredentials accountCredentials;
    private UserDAO userDAO;
    PostgreSQLJDBC postgreSQLJDBC;
    CodecoolerDAOImpl codecoolerDAO;

    public RegistrationHandle(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
        this.accountCredentials = new AccountCredentials();
        this.userDAO = new UserDAO(postgreSQLJDBC);
        this.codecoolerDAO = new CodecoolerDAOImpl(postgreSQLJDBC);

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        String method = exchange.getRequestMethod(); //post or get string
        System.out.println(method);
        exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
        exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));

        if (method.equals("POST")) {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String form = bufferedReader.readLine();
            System.out.println(form);

            Map<String, String> data = Parser.parseFormData(form);

            this.accountCredentials = new AccountCredentials();
            accountCredentials.setLogin(data.get("login"))
                    .setPassword(data.get("password"))
                    .setEmail(data.get("email"))
                    .setRoleEnum(RoleEnum.CODECOOLER);

            String firstName = data.get("firstName");
            String lastName = data.get("lastName");
            Codecooler codecooler = new Codecooler(accountCredentials, firstName, lastName);

            codecooler.setFirstName(firstName)
                    .setLastName(lastName)
                    .setAccountCredentials(accountCredentials);
            try {
                codecoolerDAO.setCodecooler(codecooler, accountCredentials);
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
