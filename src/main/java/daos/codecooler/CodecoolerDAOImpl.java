package daos.codecooler;

import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Codecooler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerDAOImpl implements CodecoolerDAO {

    private PostgreSQLJDBC postgreSQLJDBC;

    public CodecoolerDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
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
    public List<Codecooler> getCodecooler(int id) throws SQLException {
        final String SELECT_SQL = "SELECT * FROM \"Users\" WHERE id = " + id + ";";

        Statement st = postgreSQLJDBC.getConnection().createStatement();

        List<Codecooler> codecoolers = new ArrayList<>();

        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            codecoolers = createCodecoolerList(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return codecoolers;
    }

    @Override
    public void deleteCodecooler(int id) {

    }

    @Override
    public int getPurse() {
        return 0;
    }

    @Override
    public int getCoins() {
        return 0;
    }

//    private RoleEnum roleIdToEnum(int roleId) throws Exception {
//        RoleEnum roleEnum;
//        switch (roleId) {
//            case 1 -> roleEnum = RoleEnum.CREEP;
//            case 2 -> roleEnum = RoleEnum.MENTOR;
//            case 3 -> roleEnum = RoleEnum.CODECOOLER;
//            default -> throw new Exception("Wrong role id");
//        }
//        return roleEnum;
//    }

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

}
