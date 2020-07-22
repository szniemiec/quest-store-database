package daos.codecooler;

import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Codecooler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerDAOImpl implements CodecoolerDAO {

    private PostgreSQLJDBC postgreSQLJDBC;
    private ResultSet rs;
    private RoleEnum roleEnum;

    public CodecoolerDAOImpl(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    @Override
    public List<Codecooler> getCodecoolers() throws SQLException {
        List<Codecooler> codecoolers = new ArrayList<>();
        final String SELECT_SQL = "SELECT * FROM \"Users\" WHERE role_id = 3;";

        Statement st = postgreSQLJDBC.getConnection().createStatement();

        try {
            ResultSet rs = st.executeQuery(SELECT_SQL);
            while (rs.next()) {
                Codecooler codecooler = createCodecooler(rs);
                codecoolers.add(codecooler);
            }
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
            while (rs.next()) {
                Codecooler codecooler = createCodecooler(rs);
                codecoolers.add(codecooler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codecoolers.get(0);
    }

    @Override
    public void addCodecooler(Codecooler codecooler) {
        final String INSERT_SQL = "INSERT INTO \"Users\" " +
                "(login, password, email, role_id, first_name, last_name, module_id, coins) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        Connection c = postgreSQLJDBC.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(INSERT_SQL);
            ps.setString(1, codecooler.getAccountCredentials().getLogin());
            ps.setString(2, codecooler.getAccountCredentials().getPassword());
            ps.setString(3, codecooler.getAccountCredentials().getEmail());
            ps.setInt(4, codecooler.getAccountCredentials().getRoleEnum().getRoleId());
            ps.setString(5, codecooler.getFirstName());
            ps.setString(6, codecooler.getLastName());
            ps.setInt(7, codecooler.getModule().getModuleId());
            if (codecooler.getPurse() != null) {
                ps.setInt(8, codecooler.getPurse().getCoins());
            } else {
                ps.setInt(8, 0);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCodecoolerWithId(Codecooler codecooler) {
        final String INSERT_SQL = "INSERT INTO \"Users\" " +
                "(id, login, password, email, role_id, first_name, last_name, module_id, coins) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        Connection c = postgreSQLJDBC.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(INSERT_SQL);
            ps.setInt(1, codecooler.getId());
            ps.setString(2, codecooler.getAccountCredentials().getLogin());
            ps.setString(3, codecooler.getAccountCredentials().getPassword());
            ps.setString(4, codecooler.getAccountCredentials().getEmail());
            ps.setInt(5, codecooler.getAccountCredentials().getRoleEnum().getRoleId());
            ps.setString(6, codecooler.getFirstName());
            ps.setString(7, codecooler.getLastName());
            ps.setInt(8, codecooler.getModule().getModuleId());
            if (codecooler.getPurse() != null) {
                ps.setInt(9, codecooler.getPurse().getCoins());
            } else {
                ps.setInt(9, 0);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCodecooler(int id) {
        final String DELETE_SQL = "DELETE FROM \"Users\" WHERE id = ?;";
        Connection c = postgreSQLJDBC.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(DELETE_SQL);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Codecooler createCodecooler(ResultSet rs) throws Exception {
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

        return new Codecooler(id, accountCredentials, firstname, lastname, moduleEnum, purse);
    }

    public void setCodecooler(Object t, AccountCredentials accountCredentials) throws Exception {
        Connection c = postgreSQLJDBC.getConnection();
        Codecooler codecooler = (Codecooler) t;
        RoleEnum roleEnum = roleToEnum(3);
        ModuleEnum moduleEnum = moduleIdToEnum(1);
        int coins = 0;
        final String QUERY_SQL = "INSERT INTO \"Users\" " +
                "(login, password, email, role_id, first_name, last_name, module_id, coins) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = c.prepareStatement(QUERY_SQL);
            ps.setString(1, accountCredentials.getLogin());
            ps.setString(2, accountCredentials.getPassword());
            ps.setString(3, accountCredentials.getEmail());
            ps.setInt(4, 3);
            ps.setString(5, codecooler.getFirstName());
            ps.setString(6, codecooler.getLastName());
            ps.setInt(7, 1);
            ps.setInt(8, coins);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
