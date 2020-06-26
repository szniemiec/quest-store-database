package daos.Creep;

import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.AccountCredentials;
import models.users.Creep;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreepDAOImpl implements CreepDAO {
    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet result;

    public CreepDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    public List<Creep> getCreeps() throws Exception {
        List<Creep> creeps = new ArrayList<>();
        Connection c = postgreSQLJDBC.getConnection();
        Statement statement = c.createStatement();
        try {
            result = statement.executeQuery("SELECT * FROM \"Users\" WHERE role_id = 1");
            while (result.next()) {
                Creep creep = createCreep(result);
                creeps.add(creep);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return creeps;
    }

    @Override
    public Creep getCreep(int id) throws SQLException {
        final String SELECT_SQL = "SELECT * FROM \"Users\" WHERE id = " + id + ";";
        Statement st = postgreSQLJDBC.getConnection().createStatement();
        List<Creep> creeps = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            Creep creep = createCreep(rs);
            creeps.add(creep);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return creeps.get(0);
    }

    private Creep createCreep(ResultSet result) throws Exception {
        int id = result.getInt("id");
        String login = result.getString("login");
        String password = result.getString("password");
        String email = result.getString("email");
        String firstName = result.getString("first_name");
        String lastName = result.getString("last_name");

        AccountCredentials accountCredentials = new AccountCredentials(login, password, email, RoleEnum.CREEP);
        return new Creep(id, accountCredentials, firstName, lastName);
    }

    public void editCreep(int id, String databaseColumn, String newValue) {
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
    public void deleteCreep(int id) {
        final String DELETE_SQL = "DELETE FROM \"Categories\" WHERE id = ?;";
        try {
            PreparedStatement ps = this.postgreSQLJDBC.getConnection().prepareStatement(DELETE_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


