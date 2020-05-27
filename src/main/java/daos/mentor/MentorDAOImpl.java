package daos.mentor;

import database.PostgreSQLJDBC;
import enums.Role;
import jdk.internal.vm.compiler.collections.UnmodifiableEconomicMap;
import models.users.AccountCredentials;
import models.users.Mentor;
import models.users.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MentorDAOImpl {
    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet result;

    public MentorDAOImpl(PostgreSQLJDBC postgreSQLJDBC) throws SQLException {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    public List<Mentor> getMentors() throws Exception {
        Statement statement = postgreSQLJDBC.getConnection().createStatement();
        List<Mentor> mentors = new ArrayList<>();
        try {
            result = statement.executeQuery("SELECT * FROM \"Users\" WHERE role_id = 2");
            mentors = addMentorToList(result);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return mentors;
    }

    private List<Mentor> addMentorToList(ResultSet result) throws Exception {
        List<Mentor> mentors = new ArrayList<>();

        while (result.next()) {
            int id = result.getInt("id");
            String login = result.getString("login");
            String password = result.getString("password");
            String email = result.getString("email");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");

            AccountCredentials accountCredentials = new AccountCredentials(login, password, email, Role.MENTOR);
            Mentor mentor = new Mentor(id, accountCredentials, firstName, lastName);
            mentors.add(mentor);
        }
        return mentors;
    }
    public void deleteMentor (int id){

    }
}