import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import services.Reader;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        Reader reader = new Reader();

        PostgreSQLJDBC database = new PostgreSQLJDBC();

        DatabaseCredentials databaseCredentials = reader.readDatabaseEnviroment();

        database.connectToDatabase(databaseCredentials);

        database.disconnectFromDatabase();
    }
}
