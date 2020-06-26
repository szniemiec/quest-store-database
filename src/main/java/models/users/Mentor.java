package models.users;

public class Mentor extends User {

    public Mentor(int id, AccountCredentials accountCredentials, String firstName, String lastName) {
        super(id, accountCredentials, firstName, lastName);
    }

    public Mentor(AccountCredentials accountCredentials, String firstName, String lastName) {
        super(accountCredentials,  firstName, lastName);
    }
}
