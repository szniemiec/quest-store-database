package daos.loginAccess;

import database.PostgreSQLJDBC;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginAccesDAO implements LoginAccesDAOInterface {
    private PreparedStatement ps;
    private List<Integer> loginData;
    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet result;


    public LoginAccesDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public List<Integer> readLoginData(String email, String pass) {
        try {
            retrieveData(email, pass);
            return loginData;
        } catch (PSQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("No such user");
            return null;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return null;
    }

    private void retrieveData(String email, String pass) throws SQLException {
        final String SELECT_SQL = "SElECT id, access_level FROM login_access WHERE email = '"+ email +"' AND password = '"+pass+"'";
        loginData = new ArrayList<>();
        Connection c = postgreSQLJDBC.getConnection();
        Statement statement = c.createStatement();
        result = statement.executeQuery(SELECT_SQL);
        while (result.next()) {
            loginData.add(result.getInt("access_level"));
            loginData.add(result.getInt("id"));
        }
        result.close();
        statement.close();
    }

    public void saveSessionId(String sessionId, String email) {
        final String UPDATE_SQL = "UPDATE login_access SET session_id = ? WHERE email = ?;";
        Connection c = postgreSQLJDBC.getConnection();
        try {
            ps = this.postgreSQLJDBC.getConnection().prepareStatement(UPDATE_SQL);
            ps.setString(1, sessionId);
            ps.setString(2, email);
            ps.executeUpdate();
            c.commit();
            ps.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void deleteSessionID(String sessionId) {
        final String UPDATE_SQL = "UPDATE public.login_access SET session_id = null WHERE session_id = ? ;";
        Connection c = postgreSQLJDBC.getConnection();
        try {
            sessionId = sessionId.substring(1, sessionId.length() - 1);
            ps = this.postgreSQLJDBC.getConnection().prepareStatement(UPDATE_SQL);
            ps.setString(1, sessionId);
            ps.executeUpdate();
            c.commit();
            ps.close();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public String getIdBySessionId(String sessionId) throws SQLException {
        final String SELECT_SQL = "SELECT id FROM login_access WHERE session_id = '" + sessionId + "';";
        String id = "";
        Connection c = postgreSQLJDBC.getConnection();
        Statement statement = c.createStatement();
        System.out.println(sessionId);
        ResultSet resultSet = statement.executeQuery(SELECT_SQL);

        while (resultSet.next()) {
            id = resultSet.getString("id");
            System.out.println(id);
            System.out.println("DUUUUUUUUUUUUUUUUUUUUUUUUPAAAAAAAAAAAAAAa");
        }
        return id;
    }

    public boolean checkSessionPresent(String sessionId) {
        boolean sessionPresent = false;
        final String SELECT_SQL = "SELECT session_id FROM public.login_access WHERE session_id = ?";
        try {
            ps = this.postgreSQLJDBC.getConnection().prepareStatement(SELECT_SQL);
            ps.setString(1, sessionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                sessionPresent = true;
            }
            rs.close();

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return sessionPresent;
    }
}