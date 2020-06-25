package daos.Creep;

import models.users.Codecooler;
import models.users.Creep;

import java.sql.SQLException;
import java.util.List;

public interface CreepDAO {
     Creep getCreep(int id) throws SQLException;
    List<Creep> getCreeps() throws SQLException, Exception;
    void deleteCreep(int id);
}
