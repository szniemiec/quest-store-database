package models;

import enums.QuestCategoryEnum;

public class Quest {

    private int id;
    private String name;
    private String description;
    private QuestCategoryEnum questCategoryEnum;
    private int reward;

    public Quest(int id, String name, String description, QuestCategoryEnum questCategoryEnum, int reward) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questCategoryEnum = questCategoryEnum;
        this.reward = reward;
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

    public QuestCategoryEnum getQuestCategoryEnum() {
        return questCategoryEnum;
    }

    public int getReward() {
        return reward;
    }

}
