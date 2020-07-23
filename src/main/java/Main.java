import controller.LoginController;
import daos.codecooler.CodecoolerDAOImpl;
import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Codecooler;
import services.JSONService;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        PostgreSQLJDBC database = new PostgreSQLJDBC();
        JSONService jsonService = new JSONService();
        DatabaseCredentials credentials = jsonService.readEnviroment();
        database.connectToDatabase(credentials);
        LoginController loginController = new LoginController(database);
        CodecoolerDAOImpl codecoolerDAO = new CodecoolerDAOImpl(database);
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        for (Codecooler codecooler : codecoolerList) {
            if (codecooler.getAccountCredentials().getLogin().equals("test")) {
                codecoolerDAO.deleteCodecooler(codecooler.getId());
            }
        }
//        codecoolerDAO.addCodecooler(new Codecooler
//                (new AccountCredentials("lol", "lol", "lol@lol.pl", RoleEnum.MENTOR),
//                "lol", "lol", ModuleEnum.ADVANCED, new Purse(0)));
        // wpisz to co chcesz wywołać
//        loginController.startLogin();

//        database.disconnectFromDatabase();
    }
}
