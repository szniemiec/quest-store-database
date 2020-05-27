package daos.quest;

import enums.QuestCategory;
import models.Quest;

import java.sql.SQLException;
import java.util.List;

public interface QuestDAO {

    List<Quest> getQuests() throws SQLException;

    Quest getQuest(int id);

    void deleteQuest(int id);

    void addNewQuest(String name, String description, QuestCategory category, int reward);
}
