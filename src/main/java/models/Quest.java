package models;

import enums.QuestCategoryEnum;

public class Quest {

    private int id;
    private String name;
    private String description;
    private int reward;
    private QuestCategoryEnum questCategoryEnum;

    public Quest(int id, String name, String description, int reward, QuestCategoryEnum questCategoryEnum) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.questCategoryEnum = questCategoryEnum;
    }

    public Quest(String name, String description, int reward, QuestCategoryEnum questCategoryEnum) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.questCategoryEnum = questCategoryEnum;
    }

    public Quest() {

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

    public QuestCategoryEnum getQuestCategoryEnum() {
        return questCategoryEnum;
    }

    public Quest setName(String newValue) {
        this.name = name;
        return null;
    }

    public Quest setDescription(String newValue) {
        this.description = description;
        return null;
    }

    public Quest setCategory(String newValue) {
        this.questCategoryEnum = questCategoryEnum;
        return  null;
    }

    public Quest setReward(String newValue) {
        this.reward = reward;
        return null;
    }
}
