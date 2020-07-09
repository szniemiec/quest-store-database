import com.sun.net.httpserver.HttpServer;
import handler.LoginHandler;
import handler.MentorStudentsHandler;
import handler.MentorHandler;
import handler.RegistrationHandle;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import services.JSONService;

import java.net.InetSocketAddress;

public class Server {

    public static void main(String[] args) throws Exception {

        PostgreSQLJDBC database = new PostgreSQLJDBC();
        JSONService jsonService = new JSONService();
        LoginHandler login = new LoginHandler(database);

        DatabaseCredentials credentials = jsonService.readEnviroment();
        database.connectToDatabase(credentials);

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // wpisujesz scieżki które chcesz mieć w quest Store(creep. nemtor ,codecooler,login, mentorId3,questdetails, etc)
        server.createContext("/mentor", new MentorHandler(database));
        server.createContext("/login", new LoginHandler(database));
        server.createContext("/mentor/students", new MentorStudentsHandler(database));
        server.createContext("/create-mentor", new RegistrationHandle(database));
        server.setExecutor(null);
        server.start();

        System.out.println("Server has started on port 8000");
    }
//        database.disconnectFromDatabase();
}