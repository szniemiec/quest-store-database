package handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.codecooler.CodecoolerDAOImpl;
import daos.quest.QuestDAOImpl;
import database.PostgreSQLJDBC;
import helpers.Parser;
import models.Quest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public class QuestHandle implements HttpHandler {
    private QuestDAOImpl questDAO;
    PostgreSQLJDBC postgreSQLJDBC;
    CodecoolerDAOImpl codecoolerDAO;

    public QuestHandle(PostgreSQLJDBC postgreSQLJDBC) {
        this.questDAO =  new QuestDAOImpl(postgreSQLJDBC);
        this.postgreSQLJDBC = postgreSQLJDBC;
        this.codecoolerDAO = new CodecoolerDAOImpl(postgreSQLJDBC);
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

            String name = data.get("Name");
            String description = data.get("description");
            String reward = String.valueOf(data.get("reward"));

            Quest quest = new Quest(name, description, reward);
            quest.setName(data.get("name"))
                    .setDescription(data.get("description"))
                    .setReward(String.valueOf(data.get("reward")));
            try {
                questDAO.setQuest(quest);
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