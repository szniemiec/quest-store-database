package models;

public class Artifact {

    private int id;
    private String name;
    private String description;
    private int price;

    public Artifact(int id, String name, String description, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
