import controller.MentorController;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import services.JSONService;

public class Main {

    public static void main(String[] args) throws Exception {
        MentorController mentorController = new MentorController();
        JSONService jsonService = new JSONService();
        PostgreSQLJDBC database = new PostgreSQLJDBC();
        DatabaseCredentials credentials = jsonService.readEnviroment();
        database.connectToDatabase(credentials);
        mentorController.MenuMentor();
        database.disconnectFromDatabase();

    }
}
