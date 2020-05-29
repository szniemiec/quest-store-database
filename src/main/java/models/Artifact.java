package models;

public class Artifact {

    private int id;
    private String title;
    private String description;
    private int price;

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Artifact(int id, String title, String description, int price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

}
