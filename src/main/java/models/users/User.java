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

}
