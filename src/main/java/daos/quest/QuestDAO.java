package daos.quest;

import models.Quest;

import java.util.List;

public interface QuestDAO {

    public List<Quest> getQuests();

    public Quest getQuest(int id);

    public void deleteQuest(int id);

}
