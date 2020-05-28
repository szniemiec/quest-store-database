package daos.CreepyGuyDAO;

import org.postgresql.util.PSQLException;
import models.users.Mentor;
import views.View;
import models.users.Creep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public abstract class CreepyGuyDAO implements CreepyGuyDaoInterface {
    Connection connection;
    PreparedStatement ps;
    View view = new View();
    Map<String, String> mentorData;
    Map<String, String> creepyGuyData;
    private Mentor mentor;
    private int id;

    public CreepyGuyDAO(Connection connection){
        this.connection = connection;
    }

    public void addMentor(Mentor mentor) throws NumberFormatException{
        try {
            if(!mailExists(mentor)) {
                addMentorRecord(mentor);
            }
        } catch (PSQLException e){
            System.out.println("loginExists");
        }
        catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }



    public void editMentor(Mentor mentor, int id){
        try {
            mentorUpdateProcessing(mentor, id);
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    private boolean mailExists(Mentor mentorModel) throws SQLException{
        boolean emailTaken = false;
        ps = connection.prepareStatement("SELECT login_access.email FROM login_access WHERE email = ?;");
        ps.setString(1, mentorModel.getEmail());
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            emailTaken = true;
        }
        rs.close();
        ps.close();
        return emailTaken;
    }

    private void addMentorRecord(Mentor mentor) throws SQLException, NumberFormatException{
        ps = connection.prepareStatement("INSERT INTO login_access (email, password, id)"
                + "\n VALUES (?, ?, ?);");
        ps.setString(1, mentor.getEmail());
        ps.setString(2, mentor.getPassword());
        ps.setInt(3, 2);
        ps.executeUpdate();

        ps = connection.prepareStatement("INSERT INTO codecoolers (coolcoins, quest_in_progress, first_name, last_name,)"
                + "\n VALUES (?, ?, ?, ?);");
        ps.setInt(1, 0);
        ps.setInt(2, 1);
        ps.setInt(4, 0);
        ps.setInt(5, 1);
        ps.setString(6, mentor.getName());
        ps.setString(7, mentor.getSurname());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }


    private void mentorUpdateProcessing(Mentor mentor, int id)throws SQLException{
        ps = connection.prepareStatement("Update login_access SET email = ?, password = ? WHERE id = ?;");
        ps.setString(1, mentor.getEmail());
        ps.setString(2, mentor.getPassword());
        ps.setInt(3, Integer.parseInt(String.valueOf(id)));
        ps.executeUpdate();

        ps = connection.prepareStatement("Update codecoolers SET coolcoins = ?, " +
                "quest_in_progress = ?, first_name = ?, last_name = ?;");
        ps.setInt(1, 0);
        ps.setString(2, mentor.getName());
        ps.setString(3, mentor.getSurname());
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public void deleteMentor(String id) {
        try {
            deleteRecord(id);
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    private void deleteRecord(String id) throws SQLException{
        ps = connection.prepareStatement("DELETE FROM codecoolers WHERE codecooler_id = ?;");
        ps.setString(1, id);
        ps.executeUpdate();

        ps = connection.prepareStatement("DELETE FROM login_access WHERE id = ?;");
        ps.setString(1,id);
        ps.executeUpdate();
        connection.commit();
        ps.close();
    }

    public Mentor getMentorById(String id){
        try {
            fetchMentor(id);
            view.print("Operation done successfully\n");
            return new Mentor(mentorData);
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        } catch (NumberFormatException e){
            view.print("Passed ID is not numerical value");
        }catch (Exception e){
            return null;
        }
        return null;
    }

    private void fetchMentor(String id) throws Exception{
        mentorData = new HashMap<String, String>();
        ps = connection.prepareStatement("SELECT login_access.email, login_access.access_level, codecoolers.first_name, codecoolers.nickname, " +
                "codecoolers.last_name, login_access.password, codecoolers.actual_room FROM login_access " +
                " INNER JOIN codecoolers ON login_access.id = codecoolers.codecooler_id WHERE id = ?;");
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            mentorData.put("firstName", rs.getString("first_name"));
            mentorData.put("surname", rs.getString("last_name"));
            mentorData.put("email", rs.getString("email"));
            mentorData.put("password", rs.getString("password"));
            if(rs.getInt("access_level") != 2){
                throw new Exception();
            }
        }
        rs.close();
        ps.close();
    }

    public Creep getAdminBySessionId(int id){
        try {
            fetchAdmin(id);
            view.print("Operation done successfully\n");
            return new Creep(creepyGuyData);
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        } catch (NumberFormatException e){
            view.print("Passed ID is not numerical value");
        }catch (Exception e){
            return null;
        }
        return null;
    }

    private void fetchAdmin(int sessionId) throws Exception{
        creepyGuyData = new HashMap<String, String>();
        ps = connection.prepareStatement("SELECT login_access.email, login_access.access_level, codecoolers.first_name, " +
                "codecoolers.last_name, login_access.password FROM login_access " +
                " INNER JOIN codecoolers ON login_access.id = codecoolers.codecooler_id WHERE session_id = ?;");
        ps.setString(1, String.valueOf(sessionId));
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            creepyGuyData.put("firstName", rs.getString("first_name"));
            creepyGuyData.put("surname", rs.getString("last_name"));
            creepyGuyData.put("email", rs.getString("email"));
            creepyGuyData.put("password", rs.getString("password"));
            if(rs.getInt("access_level") != 3){
                throw new Exception();
            }
        }
        rs.close();
        ps.close();
    }

}
