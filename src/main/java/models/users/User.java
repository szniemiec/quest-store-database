package models.users;

public abstract class User {

    private int id;
    private AccountCredentials accountCredentials;
    private String firstName;
    private String lastName;

    public User(int id, AccountCredentials accountCredentials, String firstName, String lastName) {
        this.id = id;
        this.accountCredentials = accountCredentials;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(AccountCredentials accountCredentials, String firstName, String lastName) {
        this.accountCredentials = accountCredentials;
        this.firstName = firstName;
        this.lastName  = lastName;
    }


    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setAccountCredentials(AccountCredentials accountCredentials) {
        this.accountCredentials = accountCredentials;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public int getId() {
        return id;
    }

    public AccountCredentials getAccountCredentials() {
        return accountCredentials;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
