package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import services.JSONService;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.logging.Handler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RegistrationHandleTest {
private PostgreSQLJDBC database;
private RegistrationHandle registrationHandle;


@BeforeEach
    void prepareTest(){
    JSONService jsonService = new JSONService();
    DatabaseCredentials credentials = jsonService.readEnviroment();
    database = new PostgreSQLJDBC();
    database.connectToDatabase(credentials);
    registrationHandle = new RegistrationHandle(database);
}
    @AfterEach
    void closeTest() {
        try {
            database.disconnectFromDatabase();
        } catch (SQLException ignore) {
        }
    }

    @Test
    void handleValidTest(){
    String dataTest = "";
    }
private void processTest(String dataTest) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(dataTest.getBytes(StandardCharsets.UTF_8));
    HttpExchange httpExchangeMock = Mockito.mock(HttpExchange.class);
    when(httpExchangeMock.getRequestMethod()).thenReturn("POST");
    when(httpExchangeMock.getRequestBody()).thenReturn(inputStream);
    when(httpExchangeMock.getResponseHeaders()).thenReturn(new Headers());
    when(httpExchangeMock.getResponseBody()).thenReturn(new ByteArrayOutputStream());
    registrationHandle.handle(httpExchangeMock);
}
}