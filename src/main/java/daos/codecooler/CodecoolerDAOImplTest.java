package daos.codecooler;

import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Codecooler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import services.JSONService;

import java.sql.SQLException;
import java.util.List;

class CodecoolerDAOImplTest {

    private PostgreSQLJDBC database;
    private JSONService jsonService;
    private DatabaseCredentials credentials;
    private CodecoolerDAOImpl codecoolerDAO;
    private Codecooler testCodecooler = new Codecooler(
            new AccountCredentials("test", "test", "test@test.pl", RoleEnum.CODECOOLER),
            "test", "test", ModuleEnum.WEB, new Purse(0));

    @BeforeEach
    void prepareTest() {
        database = new PostgreSQLJDBC();
        jsonService = new JSONService();
        credentials = jsonService.readEnviroment();
        database.connectToDatabase(credentials);
        codecoolerDAO = new CodecoolerDAOImpl(database);
    }

    @AfterEach
    void closeTest() {
        try {
            database.disconnectFromDatabase();
        } catch (SQLException ignore) {
        }
    }


    @org.junit.jupiter.api.Test
    void getCodecoolersTest() throws SQLException {
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        for (Codecooler codecooler : codecoolerList) {
            Assertions.assertNotNull(codecooler);
        }
    }

    @org.junit.jupiter.api.Test
    void getCodecoolerTest() throws SQLException {
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        for (int i = 0; i <= 2; i++) {
            int randomIndex = (int) (Math.random() * codecoolerList.size() + 0);
            Codecooler codecooler = codecoolerList.get(randomIndex);
            Assertions.assertNotNull(codecooler);
        }
    }

    @org.junit.jupiter.api.Test
    void addCodecoolerTest() throws SQLException {
        codecoolerDAO.addCodecooler(testCodecooler);
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        boolean isCodecoolerInDatabase = false;
        for (Codecooler codecoolerInDatabase : codecoolerList) {
            String emailInDatabase = codecoolerInDatabase.getAccountCredentials().getEmail();
            String loginInDatabase = codecoolerInDatabase.getAccountCredentials().getLogin();
            String emailTest = testCodecooler.getAccountCredentials().getEmail();
            String loginTest = testCodecooler.getAccountCredentials().getLogin();
            if (emailInDatabase.equals(emailTest) && loginInDatabase.equals(loginTest)) {
                isCodecoolerInDatabase = true;
                break;
            }
        }
        Assertions.assertTrue(isCodecoolerInDatabase);
    }

    @org.junit.jupiter.api.Test
    void deleteCodecoolerTest() throws SQLException {
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        boolean isCodecoolerInDatabase = true;
        for (Codecooler codecoolerInDatabase : codecoolerList) {
            String emailInDatabase = codecoolerInDatabase.getAccountCredentials().getEmail();
            String loginInDatabase = codecoolerInDatabase.getAccountCredentials().getLogin();
            String emailTest = testCodecooler.getAccountCredentials().getEmail();
            String loginTest = testCodecooler.getAccountCredentials().getLogin();
            if (emailInDatabase.equals(emailTest) && loginInDatabase.equals(loginTest)) {
                isCodecoolerInDatabase = false;
                codecoolerDAO.deleteCodecooler(codecoolerInDatabase.getId());
                break;
            }
        }
        Assertions.assertFalse(isCodecoolerInDatabase);
    }

    @org.junit.jupiter.api.Test
    void setCodecoolerTest() throws Exception {
        String emailTest = "testED@testED.pl;";
        String loginTest = "testED";
        AccountCredentials testCredentials = testCodecooler.getAccountCredentials();
        testCredentials.setEmail(emailTest);
        testCredentials.setLogin(loginTest);
        testCodecooler.setAccountCredentials(testCredentials);
        codecoolerDAO.setCodecooler(testCodecooler, testCodecooler.getAccountCredentials());
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        boolean isCodecoolerSet = false;
        for (Codecooler codecoolerInDatabase : codecoolerList) {
            String emailInDatabase = codecoolerInDatabase.getAccountCredentials().getEmail();
            String loginInDatabase = codecoolerInDatabase.getAccountCredentials().getLogin();
            if (emailInDatabase.equals(emailTest) && loginInDatabase.equals(loginTest)) {
                isCodecoolerSet = true;
                codecoolerDAO.deleteCodecooler(codecoolerInDatabase.getId());
                break;
            }
        }
        Assertions.assertTrue(isCodecoolerSet);
    }


}