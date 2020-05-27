package models;

import enums.QuestCategory;

public class Quest {

    private int id;
    private String name;
    private String description;
    private int reward;
    private QuestCategory questCategory;


    public Quest(int id, String name, String description, int reward, QuestCategory questCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.questCategory = questCategory;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getReward() {
        return reward;
    }

    public QuestCategory getQuestCategory() {
        return questCategory;
    }
}
