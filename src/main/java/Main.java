import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import services.JSONService;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        JSONService jsonService = new JSONService();

        PostgreSQLJDBC database = new PostgreSQLJDBC();

        DatabaseCredentials credentials = jsonService.readEnviroment();

        database.connectToDatabase(credentials);

        database.disconnectFromDatabase();
    }
}
