package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
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

    public RegistrationHandle() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        String method = exchange.getRequestMethod();
        exchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
        exchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));

        if (method.equals("POST")) {
            InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String form = bufferedReader.readLine();
            System.out.println(form);

            Map<String, String> data = Parser.parseFormData(form);

            AccountCredentials accountCredentials = new AccountCredentials(login, password, email, roleEnum);
            Codecooler codecooler = new Codecooler(id, accountCredentials, firstName, lastName, moduleEnum, purse);

            codecooler.setFirstName(data.get("firstName"));
            codecooler.setFirstName(data.get("lastName"));
            codecooler.setLastName(data.get("lastName"));
            accountCredentials.setLogin("login");
            accountCredentials.setEmail(data.get("email"));
            accountCredentials.setPassword(data.get("password"));
            accountCredentials.setRoleEnum(RoleEnum.CODECOOLER);
            accountCredentials.setPassword("password");

            sqlUserDao.add(codecooler);

        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}
