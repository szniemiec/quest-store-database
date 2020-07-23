package daos.Creep;

import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Creep;
import models.users.User;
import org.junit.jupiter.api.*;
import services.JSONService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CreepDAOImplTest {

    private PostgreSQLJDBC database;
    private CreepDAOImpl creepDAO;
    UserDAO userDao;
    ResultSet rs = mock(ResultSet.class);
    DatabaseCredentials database;
    private User user1;
    private User user2;
    private final Creep testCreep = new Creep(
            new AccountCredentials("test", "test", "test@test.pl", RoleEnum.CREEP),
            "test", "test", ModuleEnum.WEB, new Purse(0));

    @BeforeEach
    void prepareTest() {
        database = new PostgreSQLJDBC();
        JSONService jsonService = new JSONService();
        DatabaseCredentials credentials = jsonService.readEnviroment();
        database.connectToDatabase(credentials);
        creepDAO = new CreepDAOImpl(database);
    }

    @AfterEach
    void closeTest() {
        try {
            database.disconnectFromDatabase();
        } catch (SQLException ignore) {
        }
    }

    @Test
    void setCreepTest() throws Exception {
        String emailTest = "testED@testED.pl;";
        String loginTest = "testED";
        AccountCredentials testCredentials = testCreep.getAccountCredentials();
        testCredentials.setEmail(emailTest);
        testCredentials.setLogin(loginTest);
        testCreep.setAccountCredentials(testCredentials);
        creepDAO.setCodecooler(testCreep, testCreep.getAccountCredentials());
        List<Codecooler> codecoolerList = creepDAO.getCodecoolers();
        boolean isCodecoolerSet = false;
        for (Codecooler codecoolerInDatabase : codecoolerList) {
            String emailInDatabase = codecoolerInDatabase.getAccountCredentials().getEmail();
            String loginInDatabase = codecoolerInDatabase.getAccountCredentials().getLogin();
            if (emailInDatabase.equals(emailTest) && loginInDatabase.equals(loginTest)) {
                isCodecoolerSet = true;
                creepDAO.deleteCodecooler(codecoolerInDatabase.getId());
                break;
            }
        }
        Assertions.assertTrue(isCodecoolerSet);
    }

    @Test
    void getCodecoolersTest() throws SQLException {
        List<Codecooler> codecoolerList = creepDAO.getCodecoolers();
        assertFalse(codecoolerList.contains(null));
    }
