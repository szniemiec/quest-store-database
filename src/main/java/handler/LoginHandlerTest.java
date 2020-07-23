package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.JSONService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

class LoginHandlerTest {

    private PostgreSQLJDBC database;
    private LoginHandler loginHandler;

    @BeforeEach
    void prepareTest() {
        JSONService jsonService = new JSONService();
        DatabaseCredentials credentials = jsonService.readEnviroment();
        database = new PostgreSQLJDBC();
        database.connectToDatabase(credentials);
        loginHandler = new LoginHandler(database);
    }

    @AfterEach
    void closeTest() {
        try {
            database.disconnectFromDatabase();
        } catch (SQLException ignore) {
        }
    }

    @Test
    public void handleValidTest() throws IOException {
        String testData = "login=adrian&password=adrian";
        processTest(testData);
        String expected = "{\"id\":23,\"accountCredentials\":{\"login\":\"adrian\",\"password\":" +
                "\"c23ad6f18412014673b2d04794ca038ef6767fe94afe408dffb775362fe07e68\"," +
                "\"email\":\"adrian@gmail.com\",\"roleEnum\":\"MENTOR\"},\"firstName\":\"Adrian\",\"lastName\":\"Adrian\"}";
        Assertions.assertEquals(expected, loginHandler.getResponse());
    }

    @Test
    public void handleInvalidTest() throws IOException {
        String testData = "login=adrianos&password=adrianek";
        processTest(testData);
        Assertions.assertEquals("null", loginHandler.getResponse());
    }

    private void processTest(String testData) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8));
        HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
        when(httpExchangeMock.getRequestMethod()).thenReturn("POST");
        when(httpExchangeMock.getRequestBody()).thenReturn(inputStream);
        when(httpExchangeMock.getResponseHeaders()).thenReturn(new Headers());
        when(httpExchangeMock.getResponseBody()).thenReturn(new ByteArrayOutputStream());
        loginHandler.handle(httpExchangeMock);
    }
}