package models;

import enums.QuestCategory;

public class Quest {

    private int id;
    private String name;
    private String description;
    private QuestCategory questCategory;
    private int reward;

    public Quest(int id, String name, String description, QuestCategory questCategory, int reward) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questCategory = questCategory;
        this.reward = reward;
    }

}
