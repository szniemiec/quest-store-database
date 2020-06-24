package daos;

import database.DatabaseCredentials;
import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    private PostgreSQLJDBC postgreSQLJDBC;

    public UserDAO(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    User newUser;

    public User getLoggedUser(String login, String password) throws SQLException {
        Statement st = postgreSQLJDBC.getConnection().createStatement();

        try {
            ResultSet result = st.executeQuery("SELECT * FROM \"Users\" WHERE \"login\" = '" + login + "' AND \"password\" = '" + password + "';");
            newUser = createUser(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }

    private User createUser(ResultSet result) throws Exception {
        result.next();
        int roleId = result.getInt("role_id");
        switch (roleId) {
            case 1:
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

                newUser = new Creep(id, accountCredentials, firstName, lastName);
                newUser.setId(id);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                break;

            case 2:
                id = result.getInt("id");
                login = result.getString("login");
                password = result.getString("password");
                email = result.getString("email");
                firstName = result.getString("first_name");
                lastName = result.getString("last_name");
                roleId = result.getInt("role_id");

                accountCredentials = new AccountCredentials(login, password, email, roleToEnum(roleId));
                accountCredentials.setLogin(login);
                accountCredentials.setPassword(password);
                accountCredentials.setEmail(email);

                newUser = new Mentor(id, accountCredentials, firstName, lastName);
                newUser.setId(id);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                break;

            case 3:
                id = result.getInt("id");
                login = result.getString("login");
                password = result.getString("password");
                email = result.getString("email");
                firstName = result.getString("first_name");
                lastName = result.getString("last_name");
                int moduleId = result.getInt("module_id");
                int coins = result.getInt("coins");

                accountCredentials = new AccountCredentials(login, password, email, roleToEnum(roleId));
                accountCredentials.setLogin(login);
                accountCredentials.setPassword(password);
                accountCredentials.setEmail(email);
                Purse purse = new Purse(coins);

                newUser = new Codecooler(id, accountCredentials, firstName, lastName, moduleIdToEnum(moduleId), purse);
                newUser.setId(id);
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                break;
        }
        return newUser;
    }

    private RoleEnum roleToEnum(int roleid) throws Exception {
        RoleEnum roleEnum;
        switch (roleid) {
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