package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.codecooler.CodecoolerDAOImpl;
import daos.quest.QuestDAOImpl;
import database.PostgreSQLJDBC;
import models.users.Codecooler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;


public class MentorStudentsHandler implements HttpHandler {
    QuestDAOImpl questDAO;
    CodecoolerDAOImpl studentDAO;

    public MentorStudentsHandler(PostgreSQLJDBC postgreSQLJDBC) {
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
        studentDAO = new CodecoolerDAOImpl(postgreSQLJDBC);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String uri = httpExchange.getRequestURI().getRawPath();
        String method = httpExchange.getRequestMethod();

//        if (method.equals("GET")) {
            String response = "";

            try {
                List<Codecooler> students = this.studentDAO.getCodecoolers();
                ObjectMapper mapper = new ObjectMapper();
                response = mapper.writeValueAsString(students);
                System.out.println(response);

                httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                httpExchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
                httpExchange.sendResponseHeaders(200, response.getBytes().length);

            } catch (SQLException e) {
                httpExchange.sendResponseHeaders(404, response.length());
            }
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
//}
