import daos.codecooler.CodecoolerDAOImpl;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import models.users.Codecooler;
import services.JSONService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        JSONService jsonService = new JSONService();

        PostgreSQLJDBC database = new PostgreSQLJDBC();

        DatabaseCredentials credentials = jsonService.readEnviroment();

        database.connectToDatabase(credentials);

        CodecoolerDAOImpl codecoolerDAO = new CodecoolerDAOImpl(database);
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();

        for (Codecooler c : codecoolerList) {
            System.out.println(c.getAccountCredentials().getEmail());
        }

        database.disconnectFromDatabase();
    }
}
