package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLJDBC {

    private static final String HOST = "ec2-54-247-78-30.eu-west-1.compute.amazonaws.com";
    private static final String PORT = "5432";
    private static final String DATABASE = "d5r239i6ius89k";
    private static final String LOGIN = "kjghikddzjahoq";
    private static final String PASSWORD = "df9d199d66fc0d99e2e4d883234e9ed65e5f28b14222cff4c118ebe5c2d0cfa6";
    private Connection c;

    public PostgreSQLJDBC() {
        c = null;
    }

    public void connectToDatabase() {
        try {
            c = DriverManager.getConnection("jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE, LOGIN, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public void disconnectFromDatabase() throws SQLException {
        this.c.close();
    }

}