import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import daos.mentor.MentorDAOImpl;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import handler.CreepHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.JSONService;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;


class CreepHandlerTest {

    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
    CreepHandler creepHandler = new CreepHandler(postgreSQLJDBC);

    @BeforeEach
    void prepareTest() {
        JSONService jsonService = new JSONService();
        DatabaseCredentials credentials = jsonService.readEnviroment();
        postgreSQLJDBC.connectToDatabase(credentials);
        ///

    }

    @Test
    void handle() throws Exception {
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        MentorDAOImpl mentorDaoMock = Mockito.mock(MentorDAOImpl.class);
        String expected = "{\"id\":23,\"accountCredentials\":{\"login\":\"adrian\",\"password\":" +
                "\"c23ad6f18412014673b2d04794ca038ef6767fe94afe408dffb775362fe07e68\"," +
                "\"email\":\"adrian@gmail.com\",\"roleEnum\":\"MENTOR\"},\"firstName\":\"Adrian\",\"lastName\":\"Adrian\"}";

        Mockito.when(httpExchangeMock.getRequestURI()).thenReturn(URI.create("/creep/mentors"));
        Mockito.when(mentorDaoMock.getMentors()).thenReturn(new ArrayList<>());
        Mockito.when(httpExchangeMock.getResponseHeaders()).thenReturn(new Headers());
        Mockito.when(httpExchangeMock.getResponseBody()).thenReturn(new ByteArrayOutputStream(expected.length()));
        creepHandler.handle(httpExchangeMock);
//        Assertions.assertEquals(expected, creepHandler.getResponse());
        Assertions.assertNotNull(creepHandler.getResponse());
    }

    @AfterEach
    void closeTest() {
        try {
            postgreSQLJDBC.disconnectFromDatabase();
        } catch (SQLException ignore) {
        }
    }
}