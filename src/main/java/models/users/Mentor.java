package models.users;

import java.util.Map;

public class Mentor extends User {

    private String  id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Map<String, String> collectedData;

    public Mentor(Map<String, String> mentorData){
        super();
        email = mentorData.get("email");
        password =mentorData.get("password");
        name = mentorData.get("firstName");
        surname = mentorData.get("surname");
        collectedData = mentorData;
    }

    public Map<String, String> getCollectedData() {
        return collectedData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        collectedData.put("id", id);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}
