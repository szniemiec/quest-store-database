package handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.artifact.ArtifactDAOImpl;
import daos.codecooler.CodecoolerDAOImpl;
import database.PostgreSQLJDBC;
import helpers.DataFormParser;
import models.Artifact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ArtifactHandle implements HttpHandler {
    private ArtifactDAOImpl artifactDAO;
    PostgreSQLJDBC postgreSQLJDBC;
    CodecoolerDAOImpl codecoolerDAO;
    DataFormParser dataFormParser;

    public ArtifactHandle(PostgreSQLJDBC postgreSQLJDBC) {
        this.artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);
        this.postgreSQLJDBC = postgreSQLJDBC;
        this.codecoolerDAO = new CodecoolerDAOImpl(postgreSQLJDBC);
        this.dataFormParser = new DataFormParser();
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
            Map<String, String> data = DataFormParser.getData(exchange);
            System.out.println(data);

            String name = data.get("name");
            String description = data.get("description");
            String cost = data.get("cost");

            Artifact artifact = new Artifact();
            artifact.setTitle(name)
                    .setDescription(description)
                    .setCost(cost);
            try {
                artifactDAO.setArtifact(artifact);
                sendResponse("artifact created", exchange, 201);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse("artifact NOT created", exchange, 400);
            }
        } else { //GET
            getArtifacts(exchange);
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

    private void getArtifacts(HttpExchange exchange) throws IOException {
        String response = "";
        try {
            List<Artifact> artifacts = this.artifactDAO.getArtifacts();
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.writeValueAsString(artifacts);
            System.out.println(response);
            sendResponse(response, exchange, 200);
        } catch (SQLException | JsonProcessingException throwables) {
            throwables.printStackTrace();
            sendResponse(response, exchange, 404);
        }
    }
}