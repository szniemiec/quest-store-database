package daos;

import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.AccountCredentials;
import models.users.Mentor;
import models.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;

class UserDAOTest {
    UserDAO userDao;
    ResultSet rs = mock(ResultSet.class);
    DatabaseCredentials database;
    private User user1;
    private User user2;


    @BeforeEach
    void setup() {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        postgreSQLJDBC.connectToDatabase(database);
        AccountCredentials accountCredentials1 = new AccountCredentials("tom", "tom123", "tom@op.pl", RoleEnum.MENTOR);
        AccountCredentials accountCredentials2 = new AccountCredentials("Ana", "ana123", "ana@op.pl", RoleEnum.MENTOR);
        User user1 = new Mentor(1, accountCredentials1, "Tomasz", "Nowak");
        User user2 = new Mentor(2, accountCredentials2, "Anna", "Kowalska");
    }

    @Test
    void CheckIfUserCreated() throws Exception {
        userDao.createUser(rs);

        Mockito.when(rs.getString(""))
    }

}