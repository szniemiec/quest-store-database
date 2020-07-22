package daos.codecooler;

import models.users.Codecooler;

import java.sql.SQLException;
import java.util.List;

public interface CodecoolerDAO {

    public List<Codecooler> getCodecoolers() throws SQLException;

    public Codecooler getCodecooler(int id) throws SQLException;

    public void addCodecooler(Codecooler codecooler);

    public void addCodecoolerWithId(Codecooler codecooler);

    public void deleteCodecooler(int id);

}
