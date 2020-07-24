package handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.codecooler.CodecoolerDAO;
import daos.codecooler.CodecoolerDAOImpl;
import daos.mentor.MentorDAOImpl;
import database.PostgreSQLJDBC;
import models.users.Codecooler;
import models.users.Mentor;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreepHandler implements HttpHandler {
    PostgreSQLJDBC postgreSQLJDBC;
    private MentorDAOImpl mentorDAO;
    private CodecoolerDAOImpl studentDAO;
    private String response = "";

    public CreepHandler(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
        mentorDAO = new MentorDAOImpl(postgreSQLJDBC);
        studentDAO = new CodecoolerDAOImpl(postgreSQLJDBC);
    }

    public String getResponse() {
        return response;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String url = httpExchange.getRequestURI().toString();
        String[] actions = url.split("/");
        final String STUDENTS_LIST = "/creep/students";
        final String MENTORS_LIST = "/creep/mentors";

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(Arrays.toString(actions));
        System.out.println(url);

        try {
            switch (url) {
//                "/creep/mentors"
                case MENTORS_LIST:
                    List<Mentor> mentors = this.mentorDAO.getMentors();
                    //tworzenie jsona
                    // response to to co sie poazuje na localost w przegladarce
                    response = mapper.writeValueAsString(mentors);
                    System.out.println(response);

                    httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                    //CORS policy * - zezwolenie na komunikacje z kazdym frontem
                    httpExchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
                    httpExchange.sendResponseHeaders(200, response.getBytes().length);
                    break;

//                "/creep/students"
                case STUDENTS_LIST:
                    List<Codecooler> students = this.studentDAO.getCodecoolers();
                    response = mapper.writeValueAsString(students);
                    System.out.println(response);

                    httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
                    httpExchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
                    httpExchange.sendResponseHeaders(200, response.getBytes().length);
                    break;
            }
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        } catch (Exception e) {
            httpExchange.sendResponseHeaders(404, response.length());
        }
    }
}