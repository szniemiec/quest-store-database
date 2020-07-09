package models.users;

public class Creep extends User {

    public Creep(AccountCredentials accountCredentials, String firstName, String lastName) {
        super(accountCredentials, firstName, lastName);
    }

    @Override
    public User setId(int id) {
        super.setId(id);
        return null;
    }

    @Override
    public User setAccountCredentials(AccountCredentials accountCredentials) {
        super.setAccountCredentials(accountCredentials);
        return null;
    }

    @Override
    public User setFirstName(String firstName) {
        super.setFirstName(firstName);
        return null;
    }

    @Override
    public User setLastName(String lastName) {
        super.setLastName(lastName);
        return null;
    }

    public Creep(int id, AccountCredentials accountCredentials, String firstName, String lastName) {
        super(id, accountCredentials, firstName, lastName);
    }

}
