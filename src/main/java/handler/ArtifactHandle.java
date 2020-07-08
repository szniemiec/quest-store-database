package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.artifact.ArtifactDAOImpl;
import daos.codecooler.CodecoolerDAOImpl;
import database.PostgreSQLJDBC;
import helpers.Parser;
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

    public ArtifactHandle(PostgreSQLJDBC postgreSQLJDBC) {
        this.artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);
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

            String name = data.get("name");
            String description = data.get("description");
            String cost = String.valueOf(data.get("cost"));

            Artifact artifact = new Artifact(name, description, cost);
            artifact.setTitle(data.get("name"))
                    .setDescription(data.get("description"))
                    .setCost(data.get("cost"));
            try {
                artifactDAO.setArtifact(artifact);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else { //GET
            List<Artifact> artifacts = null;
            try {
                artifacts = this.artifactDAO.getArtifacts();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.writeValueAsString(artifacts);
            System.out.println(response);
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}