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
import java.net.URI;
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

    public User loginProcess(String login, String password) throws SQLException {
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

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod(); // "post" , "get"
        System.out.println(method);
        URI uri = httpExchange.getRequestURI();

        if (method.equals("POST") && (uri.toString().equals("/login"))) {
            checkLoginAndPassword(httpExchange);
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
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

    static Map<String, String> getDataFromRequest(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        try {
            Map<String, String> data = parseFormData(br.readLine());
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /////// WRONG //////
    private void checkLoginAndPassword(HttpExchange httpExchange) {
        try {
            Map<String, String> loginData = getDataFromRequest(httpExchange);
            String login = loginData.get("login");
            String password = loginData.get("password");
            loginProcess(login, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}