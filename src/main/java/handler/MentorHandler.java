package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.mentor.MentorDAOImpl;
import database.PostgreSQLJDBC;
import models.users.Mentor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

public class MentorHandler implements HttpHandler {
    private PostgreSQLJDBC postgreSQLJDBC;
    private MentorDAOImpl mentorDAO;

    public MentorHandler(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
        mentorDAO = new MentorDAOImpl(postgreSQLJDBC);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";

        try {
            List<Mentor> mentors = this.mentorDAO.getMentors();
            //tworzenie jsona
            // response to to co sie poazuje na localost w przegladarce
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.writeValueAsString(mentors);
            System.out.println(response);

            httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
            //CORS policy * - zezwolenie na komunikacje z kazdym frontem
            httpExchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
        } catch (Exception e) {
            httpExchange.sendResponseHeaders(404, response.length());
        }
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}