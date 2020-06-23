package daos.codecooler;
import database.PostgreSQLJDBC;
import enums.ModuleEnum;
import enums.RoleEnum;
import models.Purse;
import models.users.AccountCredentials;
import models.users.Codecooler;
import java.sql.PreparedStatement;
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