import daos.UserDAO.MentorDaoImpl;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import services.JSONService;
import services.Reader;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        JSONService jsonService = new JSONService();
        PostgreSQLJDBC database = new PostgreSQLJDBC();
        MentorDaoImpl mentorDao = new MentorDaoImpl(database);
        DatabaseCredentials credentials = jsonService.readEnviroment();

        database.connectToDatabase(credentials);
        mentorDao.getMentors();
        database.disconnectFromDatabase();

    }
}
