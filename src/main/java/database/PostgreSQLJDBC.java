package database;

import services.JSONService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLJDBC {
    Connection connection;
    DatabaseCredentials databaseCredentials;
    JSONService jsonService;
    DatabaseCredentials credentials;

    public PostgreSQLJDBC() {
        Connection connection;
//        credentials = jsonService.readEnviroment();
//        connectToDatabase(credentials);
    }



    public void connectToDatabase(DatabaseCredentials databaseCredentials) {
        String HOST = databaseCredentials.getHost();
        String PORT = databaseCredentials.getPort();
        String DATABASE = databaseCredentials.getDatabase();
        String LOGIN = databaseCredentials.getLogin();
        String PASSWORD = databaseCredentials.getPassword();

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE, LOGIN, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void disconnectFromDatabase() throws SQLException {
        this.connection.close();
    }
    public Connection getConnection() {
        return connection;
    }
}