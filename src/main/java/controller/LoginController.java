package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.UserDAO;
import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.User;
import server.Http;
import services.InputService;
import view.View;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginController implements HttpHandler {
    PostgreSQLJDBC postgreSQLJDBC;
    UserDAO userDAO;
    View view;
    InputService inputService;
    boolean isRunning;

    public LoginController(PostgreSQLJDBC postgreSQLJDBC) {
       this.postgreSQLJDBC = postgreSQLJDBC;
        this.userDAO = new UserDAO(postgreSQLJDBC);
        this.view = new View();
        this.inputService = new InputService();
        isRunning = true;
    }

    public void startLogin() throws Exception {
        view.clearScreen();
        User loggingUser = loginProcess();
        menuSwitch(loggingUser);
    }

    public User loginProcess() throws SQLException {
        String login = getLoginFromUser();
        String password = getPasswordFromUser();
        User loggingUser = userDAO.getLoggedUser(login, password);
        return loggingUser;
    }

    public void menuSwitch(User loggingUser) throws Exception {

        RoleEnum roleEnum = loggingUser.getAccountCredentials().getRoleEnum();
        switch (roleEnum) {
            case CREEP:
//                CreepController creepController = new CreepController();
//                creepController.menuCreep();
                break;
            case MENTOR:
                MentorController mentorController = new MentorController(postgreSQLJDBC);
//                getResponseHeaders()
                mentorController.MenuMentor();
                break;
            case CODECOOLER:
//                CodecoolerController codecoolerController = new CodecoolerController();
//                codecoolerController.menuCodecooler();
                break;
        }
    }

    private String getLoginFromUser() {
        return inputService.stringWithMessage("Insert login: ");
    }

    private String getPasswordFromUser() {
        return inputService.stringWithMessage("Insert password: ");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "OK";
        String method = exchange.getRequestMethod(); // "post" , "get"
        System.out.println(method);
        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            Map<String, String> data = parseFormData(br.readLine());
            String login = data.get("login");
            String password = data.get("password");
            // podepnij pod metody logowania i przekaz login i passowrd jakos data.get

            try {
                User loggingUser = userDAO.getLoggedUser(login, password);
               response = loggingUser.getFirstName();

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }

        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(), 0, response.length());
        os.close();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}