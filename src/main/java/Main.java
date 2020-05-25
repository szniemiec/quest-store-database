import database.PostgreSQLJDBC;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        PostgreSQLJDBC database = new PostgreSQLJDBC();
        database.connectToDatabase();
        database.disconnectFromDatabase();
    }
}
