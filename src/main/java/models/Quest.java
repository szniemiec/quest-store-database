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
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestCategoryEnum(QuestCategoryEnum questCategoryEnum) {
        this.questCategoryEnum = questCategoryEnum;
    }

    public Quest setCategory(String category) {
        this.questCategoryEnum = questCategoryEnum;
        return  null;
    }

    public Quest setReward(int reward) {
        this.reward = reward;
        return null;
    }
}
