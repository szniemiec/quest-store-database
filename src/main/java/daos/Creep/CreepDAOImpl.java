package daos.Creep;

import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Codecooler;
import models.users.Mentor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreepDAOImpl implements CreepDAO {
    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet result;

    public CreepDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }


    public List<Mentor> getMentors() throws Exception {
        Statement statement = postgreSQLJDBC.getConnection().createStatement();
        List<Mentor> mentors = new ArrayList<>();
        try {
            result = statement.executeQuery("SELECT * FROM \"Users\" WHERE role_id = 2");
            Mentor mentor = createMentor(result);
            mentors = addMentorToList(mentor);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return mentors;
    }
    @Override
    public void getMentor(int id) {

    }
    private List<Mentor> addMentorToList(Mentor mentor) throws Exception {
        List<Mentor> mentors = new ArrayList<>();
        mentor = createMentor(result);
        mentors.add(mentor);

        return mentors;
    }

    private Mentor createMentor(ResultSet result) throws Exception {

        while (result.next()) {
            int id = result.getInt("id");
            String login = result.getString("login");
            String password = result.getString("password");
            String email = result.getString("email");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");

            AccountCredentials accountCredentials = new AccountCredentials(login, password, email, RoleEnum.MENTOR);
            return new Mentor(id, accountCredentials, firstName, lastName);
        }
        return null;
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
    @Override
    public List<Codecooler> getCodecoolers() throws SQLException {
        final String SELECT_SQL = "SELECT * FROM \"Users\" WHERE role_id = 3;";
        Statement st = postgreSQLJDBC.getConnection().createStatement();
        List<Codecooler> codecoolers = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            codecoolers = createCodecoolerList(rs);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return codecoolers;
    }
    @Override
    public Codecooler getCodecooler(int id) throws SQLException {
        final String SELECT_SQL = "SELECT * FROM \"Users\" WHERE id = " + id + ";";
        Statement st = postgreSQLJDBC.getConnection().createStatement();
        List<Codecooler> codecoolers = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            codecoolers = createCodecoolerList(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codecoolers.get(0);
    }
    @Override
    public void deleteCodecooler(int id) {
        final String DELETE_SQL = "DELETE FROM \"Categories\" WHERE id = ?;";
        try {
            PreparedStatement ps = this.postgreSQLJDBC.getConnection().prepareStatement(DELETE_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private List<Codecooler> createCodecoolerList(ResultSet rs) throws Exception {
        List<Codecooler> codecoolers = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String email = rs.getString("email");
            String firstname = rs.getString("first_name");
            String lastname = rs.getString("last_name");
            int moduleId = rs.getInt("module_id");
            ModuleEnum moduleEnum = moduleIdToEnum(moduleId);
            int coins = rs.getInt("coins");
            Purse purse = new Purse(coins);
            AccountCredentials accountCredentials = new AccountCredentials(login, password, email, RoleEnum.CODECOOLER);
            Codecooler codecooler = new Codecooler(id, accountCredentials, firstname, lastname, moduleEnum, purse);
            codecoolers.add(codecooler);
        }
        return codecoolers;
    }
    private ModuleEnum moduleIdToEnum(int moduleId) throws Exception {
        ModuleEnum moduleEnum;
        switch (moduleId) {
            case 1 -> moduleEnum = ModuleEnum.PROG_BASICS;
            case 2 -> moduleEnum = ModuleEnum.JAVA_OOP;
            case 3 -> moduleEnum = ModuleEnum.WEB;
            case 4 -> moduleEnum = ModuleEnum.ADVANCED;
            default -> throw new Exception("Wrong module id");
        }
        return moduleEnum;
    }
}


