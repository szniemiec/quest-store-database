import controller.LoginController;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import services.JSONService;

public class Main {

    public static void main(String[] args) throws Exception {
        JSONService jsonService = new JSONService();
        PostgreSQLJDBC database = new PostgreSQLJDBC();
        DatabaseCredentials credentials = jsonService.readEnviroment();

        database.connectToDatabase(credentials);

        LoginController loginController = new LoginController(database);
        // wpisz to co chcesz wywołać
        loginController.startLogin();

        database.disconnectFromDatabase();
    }
}
