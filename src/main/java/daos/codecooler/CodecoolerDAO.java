package daos.codecooler;

import models.users.Codecooler;

import java.util.List;

public interface CodecoolerDAO {

    public List<Codecooler> getCodecoolers();

    public void getCodecooler(int id);

    public void deleteCodecooler(int id);

    public int getPurse();

    public int getCoins();

}
