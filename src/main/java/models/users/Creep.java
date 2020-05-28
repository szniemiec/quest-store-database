package models.users;


import java.util.Map;

public class Creep extends User{
    private String  id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Map<String, String> collectedData;

    public Creep(Map<String, String> creepyGuyData){
        email = creepyGuyData.get("email");
        password =creepyGuyData.get("password");
        name = creepyGuyData.get("firstName");
        surname = creepyGuyData.get("surname");
        collectedData = creepyGuyData;
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
