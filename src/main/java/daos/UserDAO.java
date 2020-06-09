package daos;

import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.*;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

    private PostgreSQLJDBC postgreSQLJDBC;

    public UserDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    User newUser;
    ResultSet rs;
    Statement st;
    Connection c;

    public User getLoggedUser(String login, String password) throws SQLException {
        Statement st = postgreSQLJDBC.getConnection().createStatement();

        try {
            ResultSet rs = st.executeQuery("SELECT * FROM \"Users\" WHERE login = '" + login + "' AND password = '" + password + "';");
            createUser(rs);
        } catch (Exception e) {
            System.out.println("User not found. Try again!");
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(String sql) {
        PostgreSQLJDBC postgreSQLJDBC = new PostgreSQLJDBC();
        Connection connection = postgreSQLJDBC.getConnection();
        try {
            this.st =
                    postgreSQLJDBC.getConnection().createStatement();
            ResultSet rs
                    = this.st.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            System.out.println("Error! Cannot execute query!");
            e.printStackTrace();
        }
        return this.rs;
    }

    private User createUser(ResultSet result) throws Exception {

        int roleId = result.getInt("role_id");

        switch (roleId) {

            case 1:
                result.next();
                int id = result.getInt("id");
                String login = result.getString("login");
                String password = result.getString("password");
                String email = result.getString("email");
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");

                AccountCredentials accountCredentials = new AccountCredentials(login, password, email, roleToEnum(id));
                accountCredentials.setLogin(login);
                accountCredentials.setPassword(password);
                accountCredentials.setEmail(email);
//                User newUser;
                newUser = new Creep(id, accountCredentials, firstName, lastName);
                newUser.setId(id);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
//                return newUser;
                break;
            case 2:
                result.next();
                id = result.getInt("id");
                login = result.getString("login");
                password = result.getString("password");
                email = result.getString("email");
                firstName = result.getString("first_name");
                lastName = result.getString("last_name");

                accountCredentials = new AccountCredentials(login, password, email, roleToEnum(id));
                accountCredentials.setLogin(login);
                accountCredentials.setPassword(password);
                accountCredentials.setEmail(email);
//                    User newUser;
                newUser = new Mentor(id, accountCredentials, firstName, lastName);
                newUser.setId(id);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
//                return newUser;
                break;
            case 3:
                result.next();
                id = result.getInt("id");
                login = result.getString("login");
                password = result.getString("password");
                email = result.getString("email");
                firstName = result.getString("first_name");
                lastName = result.getString("last_name");
                int moduleId = result.getInt("module_id");
                int coins = result.getInt("coins");

                accountCredentials = new AccountCredentials(login, password, email, roleToEnum(id));
                accountCredentials.setLogin(login);
                accountCredentials.setPassword(password);
                accountCredentials.setEmail(email);
                Purse purse = new Purse(coins);

                newUser = new Codecooler(id, accountCredentials, firstName, lastName, moduleIdToEnum(moduleId), purse);
                newUser.setId(id);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
//                return newUser;
                break;
        }
        return newUser;
    }

    private RoleEnum roleToEnum(int id) throws Exception {
        RoleEnum roleEnum;
        switch (id) {
            case 1 -> roleEnum = RoleEnum.CREEP;
            case 2 -> roleEnum = RoleEnum.MENTOR;
            case 3 -> roleEnum = RoleEnum.CODECOOLER;
            default -> throw new Exception("Wrong role id");
        }
        return roleEnum;
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
