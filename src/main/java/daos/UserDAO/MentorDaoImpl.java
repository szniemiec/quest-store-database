package daos.UserDAO;

import database.PostgreSQLJDBC;
import models.users.Mentor;
import models.users.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MentorDaoImpl {
    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet result;

    public MentorDaoImpl(PostgreSQLJDBC postgreSQLJDBC) throws SQLException {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    public List<User> getMentors() throws SQLException {
        Statement st = postgreSQLJDBC.getConnection().createStatement();

        try {
            result = st.executeQuery("SELECT * FROM \"Users\" WHERE role_id = 2");
            while (result.next()) {
                int id = result.getInt("id");
                String login = result.getString("login");
                String password = result.getString("password");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");

                String format = "|%1$-2s|%2$-5s|%3$-5s|%4$-5s|%5$-5s|\n";
                System.out.printf(format,id,login,password,firstName,lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


