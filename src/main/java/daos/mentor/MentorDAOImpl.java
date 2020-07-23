package daos.mentor;

import daos.UserDAO;
import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.AccountCredentials;

import models.users.Codecooler;
import models.users.Mentor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MentorDAOImpl implements MentorDAO {
    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet result;
    private UserDAO userDAO;

    public MentorDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    public List<Mentor> getMentors() throws Exception {
        List<Mentor> mentors = new ArrayList<>();
        Connection c = postgreSQLJDBC.getConnection();
        Statement statement = c.createStatement();
        try {
            result = statement.executeQuery("SELECT * FROM \"Users\" WHERE role_id = 2");
            while (result.next()) {
                Mentor mentor = createMentor(result);
                mentors.add(mentor);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return mentors;
    }

    @Override
    public void getMentor(int id) {

    }

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

    public void setMentor(Object t, AccountCredentials accountCredentials) {
        Connection c = postgreSQLJDBC.getConnection();
        Mentor mentor = (Mentor) t;
        int coins = 0;
        final String QUERY_SQL = "INSERT INTO \"Users\" (login, password, email, role_id, first_name, last_name) " +
                "VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ps = c.prepareStatement(QUERY_SQL);
            ps.setString(1, accountCredentials.getLogin());
            ps.setString(2, accountCredentials.getPassword());
            ps.setString(3, accountCredentials.getEmail());
            ps.setInt(4, 2);
            ps.setString(5, mentor.getFirstName());
            ps.setString(6, mentor.getLastName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMentor(Mentor mentor) {
        final String INSERT_SQL = "INSERT INTO \"Users\" " +
                "(login, password, email, role_id, first_name, last_name, module_id, coins) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        Connection c = postgreSQLJDBC.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(INSERT_SQL);
            ps.setString(1, mentor.getAccountCredentials().getLogin());
            ps.setString(2, mentor.getAccountCredentials().getPassword());
            ps.setString(3, mentor.getAccountCredentials().getEmail());
            ps.setInt(4, mentor.getAccountCredentials().getRoleEnum().getRoleId());
            ps.setString(5, mentor.getFirstName());
            ps.setString(6, mentor.getLastName());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}