package models;

public class Artifact {

    private int id;
    private String title;
    private String description;
    private String cost;

    public Artifact(int id, String title, String description, String cost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
    }

    public Artifact(String title, String description, String cost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
    }

    public Artifact() {

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

    public String getCost() {
        return cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Artifact setTitle(String title) {
        this.title = title;
        return null;
    }

    public Artifact setDescription(String description) {
        this.description = description;
        return null;
    }

    public Artifact setCost(String cost) {
        this.cost = cost;
        return null;
    }
}
