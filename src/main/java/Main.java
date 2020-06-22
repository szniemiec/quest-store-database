import controller.LoginController;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import server.Http;
import services.JSONService;

public class Main {

    public static void main(String[] args) throws Exception {
        Http http = new Http();
        JSONService jsonService = new JSONService();
        PostgreSQLJDBC database = new PostgreSQLJDBC();
        DatabaseCredentials credentials = jsonService.readEnviroment();
        http.getHttp();

        database.connectToDatabase(credentials);

        LoginController loginController = new LoginController(database);
        // wpisz to co chcesz wywołać
        loginController.startLogin();

        database.disconnectFromDatabase();
    }
}
