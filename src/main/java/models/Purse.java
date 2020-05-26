package models;

import enums.ArtifactStatus;
import enums.QuestStatus;

import java.util.HashMap;
import java.util.Map;

public class Purse {

    private int coins;
    private Map<Quest, QuestStatus> questList;
    private Map<Artifact, ArtifactStatus> artifactList;

    public Purse() {
        this.coins = 0;
        questList = new HashMap<Quest, QuestStatus>();
        artifactList = new HashMap<Artifact, ArtifactStatus>();
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

}
