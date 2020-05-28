package daos.mentor;

import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.AccountCredentials;
import models.users.Mentor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MentorDAOImpl implements MentorDAO {
    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet result;


    public MentorDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
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

    @Override
    public void getMentor(int id) {

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

            AccountCredentials accountCredentials = new AccountCredentials(login, password, email, RoleEnum.MENTOR);
            Mentor mentor = new Mentor(id, accountCredentials, firstName, lastName);
            mentors.add(mentor);
        }
        return mentors;
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
        final String DELETE_SQL = "UPDATE \"Users\" SET "+ databaseColumn + "= ? WHERE id = ?;";

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