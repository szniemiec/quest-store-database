import controller.MentorController;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import services.JSONService;

public class Main {

    public static void main(String[] args) throws Exception {
        JSONService jsonService = new JSONService();
        PostgreSQLJDBC database = new PostgreSQLJDBC();
        MentorController controller = new MentorController();
        DatabaseCredentials credentials = jsonService.readEnviroment();
        database.connectToDatabase(credentials);
        controller.MenuMentor();
        database.disconnectFromDatabase();


    }
}
