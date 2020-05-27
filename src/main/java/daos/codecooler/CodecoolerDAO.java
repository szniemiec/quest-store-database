package daos.codecooler;

import models.users.Codecooler;

import java.sql.SQLException;
import java.util.List;

public interface CodecoolerDAO {

    public List<Codecooler> getCodecoolers() throws SQLException;

    public void getCodecooler(int id);

    public void deleteCodecooler(int id);

    public int getPurse();

    public int getCoins();

}
