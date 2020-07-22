package daos.mentor;

import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.AccountCredentials;
import models.users.Mentor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class MentorDAOImplTest {
    private MentorDAOImpl mentorDAO;
    DatabaseCredentials database;
    PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();

    @BeforeEach
    void setup() {
        postgreSQLJDBC.connectToDatabase(database);
        AccountCredentials accountCredentials1 = new AccountCredentials("tom", "tom123", "tom@op.pl", RoleEnum.MENTOR);
        AccountCredentials accountCredentials2 = new AccountCredentials("Ana", "ana123", "ana@op.pl", RoleEnum.MENTOR);
        Mentor mentor = new Mentor(1, accountCredentials1, "Tomasz", "Nowak");
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
        for (Mentor mentor : mentorList) {
            Assertions.assertNotNull(mentor);
        }
    }

    @Test
    void getMentorTest() throws Exception {
        List<Mentor> mentorList = mentorDAO.getMentors();
        for (int i = 0; i <= 2; i++) {
            int randomIndex = (int) (Math.random() * mentorList.size() + 0);
            Mentor mentor = mentorList.get(randomIndex);
            Assertions.assertNotNull(mentor);
        }
    }

    @Test
    void deleteMentorTest() throws Exception {
        List<Mentor> mentorList = mentorDAO.getMentors();
        boolean isMentorInDatabase = true;
        for (Mentor mentor : mentorList) {
            String emailInDatabase = mentor.getAccountCredentials().getEmail();
            String loginInDatabase = mentor.getAccountCredentials().getLogin();
            String emailTest = mentor.getAccountCredentials().getEmail();
            String loginTest = mentor.getAccountCredentials().getLogin();
            if (emailInDatabase.equals(emailTest) && loginInDatabase.equals(loginTest)) {
                isMentorInDatabase = false;
                mentorDAO.deleteMentor(mentor.getId());
                break;
            }
        }
        Assertions.assertFalse(isMentorInDatabase);
    }
}