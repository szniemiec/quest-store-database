//import daos.Creep.CreepDAOImpl;
//import daos.UserDAO;
//import database.DatabaseCredentials;
//import database.PostgreSQLJDBC;
//import enums.ModuleEnum;
//import enums.RoleEnum;
//import models.Purse;
//import models.users.*;
//import org.junit.jupiter.api.*;
//import org.mockito.Mockito;
//import services.JSONService;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//import java.util.function.Predicate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CreepDAOImplTest {
//
//    private PostgreSQLJDBC database;
//    private CreepDAOImpl creepDAO;
//    UserDAO userDao;
//    ResultSet rs = Mockito.mock(ResultSet.class);
//    private User user1;
//    private User user2;
//    private final Creep testCreep = new Creep(
//            new AccountCredentials("test", "test", "test@test.pl", RoleEnum.CREEP),
//            "test", "test", ModuleEnum.WEB, new Purse(0));
//
//    @BeforeEach
//    void prepareTest() {
//        database = new PostgreSQLJDBC();
//        JSONService jsonService = new JSONService();
//        DatabaseCredentials credentials = jsonService.readEnviroment();
//        database.connectToDatabase(credentials);
//        creepDAO = new CreepDAOImpl(database);
//    }
//
//    @AfterEach
//    void closeTest() {
//        try {
//            database.disconnectFromDatabase();
//        } catch (SQLException ignore) {
//        }
//    }
//
//    @Test
//    void setCreepTest() throws Exception {
//        String emailTest = "testED@testED.pl;";
//        String loginTest = "testED";
//        AccountCredentials testCredentials = testCreep.getAccountCredentials();
//        testCredentials.setEmail(emailTest);
//        testCredentials.setLogin(loginTest);
//        testCreep.setAccountCredentials(testCredentials);
//        creepDAO.setCodecooler(testCreep, testCreep.getAccountCredentials());
//        List<Codecooler> codecoolerList = creepDAO.getCodecoolers();
//        boolean isCodecoolerSet = false;
//        for (Codecooler codecoolerInDatabase : codecoolerList) {
//            String emailInDatabase = codecoolerInDatabase.getAccountCredentials().getEmail();
//            String loginInDatabase = codecoolerInDatabase.getAccountCredentials().getLogin();
//            if (emailInDatabase.equals(emailTest) && loginInDatabase.equals(loginTest)) {
//                isCodecoolerSet = true;
//                creepDAO.deleteCodecooler(codecoolerInDatabase.getId());
//                break;
//            }
//        }
//        Assertions.assertTrue(isCodecoolerSet);
//    }
//
//    @Test
//    void getCodecoolersTest() throws SQLException {
//        List<Codecooler> codecoolerList = creepDAO.getCodecoolers();
//        assertFalse(codecoolerList.contains(null));
//    }
//
//    @Test
//    void addCodecoolerTest() throws SQLException {
//        creepDAO.addCodecooler(testCodecooler);
//        List<Codecooler> codecoolerList = creepDAO.getCodecoolers();
//        boolean codecoolerFound = codecoolerList.stream()
//                .map(User::getAccountCredentials)
//                .anyMatch(matchingCredentials());
//        assertTrue(codecoolerFound);
//    }
//
//    private Predicate<AccountCredentials> matchingCredentials() {
//        return accountCredentials -> testCreep.getAccountCredentials().equals(accountCredentials);
//    }
//
//    @BeforeEach
//    void setup() {
//        postgreSQLJDBC.connectToDatabase(credentials);
//        creepDAO = new CreepDAOImpl(postgreSQLJDBC);
//        AccountCredentials accountCredentials1 = new AccountCredentials("tom", "tom123", "tom@op.pl", RoleEnum.MENTOR);
//        AccountCredentials accountCredentials2 = new AccountCredentials("Ana", "ana123", "ana@op.pl", RoleEnum.MENTOR);
//        creepTest = new Creep(1, accountCredentials1, "Tomasz", "Nowak");
//        Creep creep2 = new Creep(2, accountCredentials2, "Anna", "Kowalska");
//    }
//
//    @AfterEach
//    void closeTest() {
//        try {
//            postgreSQLJDBC.disconnectFromDatabase();
//        } catch (SQLException ignore) {
//        }
//    }
//
//    @Test
//    void getMentorsTest() throws Exception {
//        List<Mentor> mentorList = creepDAO.getMentors();
//        Assertions.assertFalse(mentorList.contains(null));
//    }
//
//    @Test
//    void getMentorTest() throws Exception {
//        List<Mentor> mentorList = creepDAO.getMentors();
//        Assertions.assertNotNull(mentorList.contains(null));
//    }
//
//    @Test
//    void addMentorTest() throws Exception {
//        creepDAO.addMentor(mentorTest);
//        List<Mentor> mentorList = creepDAO.getMentors();
//        boolean isMentorInDatabase = mentorList.stream()
//                .map(User::getAccountCredentials)
//                .anyMatch(matchingCredentials());
//        Assertions.assertTrue(isMentorInDatabase);
//    }
//
//    private Predicate<AccountCredentials> matchingCredentials() {
//        return accountCredentials -> mentorTest.getAccountCredentials().equals(accountCredentials);
//    }
//
//}