package models;

public class Artifact {

    private int id;
    private String title;
    private String description;
    private int cost;

    public Artifact(int id, String title, String description, int cost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
    }

    public Artifact(String title, String description, int cost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

}
