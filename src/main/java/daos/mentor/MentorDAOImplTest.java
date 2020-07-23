package daos.mentor;

import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.AccountCredentials;
import models.users.Mentor;

import models.users.User;
import org.junit.jupiter.api.*;
import services.JSONService;

import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

class MentorDAOImplTest {
    Mentor mentorTest;
    JSONService jsonService = new JSONService();
    DatabaseCredentials credentials = jsonService.readEnviroment();
    private MentorDAOImpl mentorDAO;
    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

    @BeforeEach
    void setup() {
        postgreSQLJDBC.connectToDatabase(credentials);
        mentorDAO = new MentorDAOImpl(postgreSQLJDBC);
        AccountCredentials accountCredentials1 = new AccountCredentials("tom", "tom123", "tom@op.pl", RoleEnum.MENTOR);
        AccountCredentials accountCredentials2 = new AccountCredentials("Ana", "ana123", "ana@op.pl", RoleEnum.MENTOR);
        mentorTest = new Mentor(1, accountCredentials1, "Tomasz", "Nowak");
        Mentor mentor2 = new Mentor(2, accountCredentials2, "Anna", "Kowalska");
    }

    @AfterEach
    void closeTest() {
        try {
            postgreSQLJDBC.disconnectFromDatabase();
        } catch (SQLException ignore) {
        }
    }

    @Test
    void getMentorsTest() throws Exception {
        List<Mentor> mentorList = mentorDAO.getMentors();
        Assertions.assertFalse(mentorList.contains(null));
    }

    @Test
    void getMentorTest() throws Exception {
        List<Mentor> mentorList = mentorDAO.getMentors();
        Assertions.assertNotNull(mentorList.contains(null));
    }

    @Test
    void addMentorTest() throws Exception {
        mentorDAO.addMentor(mentorTest);
        List<Mentor> mentorList = mentorDAO.getMentors();
        boolean isMentorInDatabase = mentorList.stream()
                .map(User::getAccountCredentials)
                .anyMatch(matchingCredentials());
        Assertions.assertTrue(isMentorInDatabase);
    }

    private Predicate<AccountCredentials> matchingCredentials() {
        return accountCredentials -> mentorTest.getAccountCredentials().equals(accountCredentials);
    }
}