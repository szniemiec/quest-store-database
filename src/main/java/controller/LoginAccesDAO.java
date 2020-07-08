package controller;

import models.users.Mentor;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginAccesDAO implements LoginAccesDAOInterface {
    private PreparedStatement ps;
    private Connection connection;
    private List<Integer> loginData;


    public LoginAccesDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Integer> readLoginData(String email, String pass) {
        try{
            retriveData(email, pass);
            return loginData;
        }catch (PSQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("No such user");
            return null;
        }catch (SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return null;
    }

    private void retriveData(String email, String pass) throws SQLException {
        loginData = new ArrayList<>();
        Statement stmt = connection.createStatement();
        String sql = String.format("SElECT id, access_level FROM login_access WHERE email = '%s' AND password = '%s' ", email, pass);
        ResultSet rs = stmt.executeQuery(sql);
        while ( rs.next() ) {
            loginData.add(rs.getInt("access_level"));
            loginData.add(rs.getInt("id"));
        }
        rs.close();
        stmt.close();
    }

    public void saveSessionId(String sessionId, String email){
        try {
            ps = connection.prepareStatement("UPDATE login_access SET session_id = ? WHERE email = ?;");
            ps.setString(1, sessionId);
            ps.setString(2, email);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void deleteSessionID(String sessionId){
        try {
            sessionId = sessionId.substring(1, sessionId.length()-1);
            ps = connection.prepareStatement("UPDATE public.login_access SET session_id = null WHERE session_id = ? ;");
            ps.setString(1, sessionId);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public String getIdBySessionId(String sessionId) throws SQLException {
        String id = "";
        Statement stmt = connection.createStatement();
        System.out.println(sessionId);
        String idQuery = "SELECT id FROM login_access WHERE session_id = '" + sessionId + "';";
        ResultSet resultSet = stmt.executeQuery(idQuery);
        while(resultSet.next()){

            id = resultSet.getString("id");
            System.out.println(id);
            System.out.println("DUUUUUUUUUUUUUUUUUUUUUUUUPAAAAAAAAAAAAAAa");
        }
        return id;
    }

    public boolean checkSessionPresent(String sessionId){
        boolean sessionPresent = false;
        try{
            ps = connection.prepareStatement("SELECT session_id FROM public.login_access WHERE session_id = ?");
            ps.setString(1, sessionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                sessionPresent = true;
            }
            rs.close();
        }catch (SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return sessionPresent;
    }

    @Override
    public void addMentor(Mentor mentor) {

    }

    @Override
    public void editMentor(Mentor mentor, String id) {

    }

    @Override
    public void deleteMentor(String id) {

    }

    @Override
    public Mentor getMentorById(String id) {
        return null;
    }
}