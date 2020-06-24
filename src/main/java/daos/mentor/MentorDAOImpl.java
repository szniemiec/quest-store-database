package daos.mentor;

import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.AccountCredentials;
import models.users.Mentor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorDAOImpl implements MentorDAO {
    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet result;

    public MentorDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    public List<Mentor> getMentors() throws Exception {
        List<Mentor> mentors = new ArrayList<>();
        Connection c = postgreSQLJDBC.getConnection();
        Statement statement = c.createStatement();
        try {
            result = statement.executeQuery("SELECT * FROM \"Users\" WHERE role_id = 2");
           while(result.next()) {
               Mentor mentor = createMentor(result);
               mentors.add(mentor);
           }
//            mentors = addMentorToList(mentor);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return mentors;
    }

    @Override
    public void getMentor(int id) {

    }

//    private List<Mentor> addMentorToList(Mentor mentor) throws Exception {
//        List<Mentor> mentors = new ArrayList<>();
//        mentor = createMentor(result);
//        mentors.add(mentor);
//
//        return mentors;
//    }

    private Mentor createMentor(ResultSet result) throws Exception {
            int id = result.getInt("id");
            String login = result.getString("login");
            String password = result.getString("password");
            String email = result.getString("email");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");

            AccountCredentials accountCredentials = new AccountCredentials(login, password, email, RoleEnum.MENTOR);
            return new Mentor(id, accountCredentials, firstName, lastName);
    }

    @Override
    public void deleteMentor(int id) {
        final String DELETE_SQL = "DELETE FROM \"Users\" WHERE id = ?;";

        try {
            PreparedStatement preparedStatement = this.postgreSQLJDBC.getConnection().prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editMentor(int id, String databaseColumn, String newValue) {
        final String DELETE_SQL = "UPDATE \"Users\" SET " + databaseColumn + "= ? WHERE id = ?;";

        try {
            PreparedStatement preparedStatement = this.postgreSQLJDBC.getConnection().prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, newValue);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}