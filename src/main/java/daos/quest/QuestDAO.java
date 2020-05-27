package daos.quest;

import models.Quest;

import java.sql.SQLException;
import java.util.List;

public interface QuestDAO {

    public List<Quest> getQuests() throws SQLException;

    public Quest getQuest(int id) throws SQLException;

    public void deleteQuest(int id);

}
