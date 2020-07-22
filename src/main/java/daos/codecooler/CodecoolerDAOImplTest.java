package daos.codecooler;

import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Codecooler;
import models.users.User;
import org.junit.jupiter.api.*;
import services.JSONService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class CodecoolerDAOImplTest {

    private PostgreSQLJDBC database;
    private CodecoolerDAOImpl codecoolerDAO;
    private final Codecooler testCodecooler = new Codecooler(
            new AccountCredentials("test", "test", "test@test.pl", RoleEnum.CODECOOLER),
            "test", "test", ModuleEnum.WEB, new Purse(0));

    @BeforeEach
    void prepareTest() {
        database = new PostgreSQLJDBC();
        JSONService jsonService = new JSONService();
        DatabaseCredentials credentials = jsonService.readEnviroment();
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


    @Test
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

    @Test
    void getCodecoolersTest() throws SQLException {
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        assertFalse(codecoolerList.contains(null));
    }

    @Test
    void addCodecoolerTest() throws SQLException {
        // Act
        codecoolerDAO.addCodecooler(testCodecooler);
        // Assert
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        boolean codecoolerFound = codecoolerList.stream()
                .map(User::getAccountCredentials)
                .anyMatch(matchingCredentials());
        assertTrue(codecoolerFound);
    }

    private Predicate<AccountCredentials> matchingCredentials() {
        return accountCredentials -> testCodecooler.getAccountCredentials().equals(accountCredentials);
    }

    @Test
    void deleteCodecoolerTest() throws SQLException {
        // metoda -> znajdź codecoolera o zgodnych credentialach w liście
        List<Codecooler> codecoolerList = codecoolerDAO.getCodecoolers();
        boolean isCodecoolerInDatabase = true;
        // Codecooler ccer = getCCWithCreden(lista)
        // dao.delete(ccer)
        // Codecooler cer2 = dao.get(ccer.id())
        Assertions.assertFalse(isCodecoolerInDatabase);
    }


}