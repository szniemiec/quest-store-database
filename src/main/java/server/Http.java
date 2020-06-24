package server;

import com.sun.net.httpserver.HttpServer;
import controller.LoginController;
import controller.MentorController;
import database.PostgreSQLJDBC;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Http {
    PostgreSQLJDBC postgreSQLJDBC;

    public Http(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    public void getHttp() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // wpisujesz scieżki które chcesz mieć w quest Store(creep. nemtor ,codecooler,login, mentorId3,questdetails, etc)
        server.createContext("/mentor", new MentorController(postgreSQLJDBC));
        server.createContext("/login", new LoginController(postgreSQLJDBC));
        server.setExecutor(null);
        server.start();

        System.out.println("Server has started on port 8000");
    }


}
