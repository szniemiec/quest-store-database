package daos.Creep;

import models.users.Codecooler;

import java.sql.SQLException;
import java.util.List;

public interface CreepDAO {
    void getMentor(int id);

    void deleteMentor(int id);

    List<Codecooler> getCodecoolers() throws SQLException;

    Codecooler getCodecooler(int id) throws SQLException;

    void deleteCodecooler(int id);
}
