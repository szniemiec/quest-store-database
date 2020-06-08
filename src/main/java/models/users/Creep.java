package models.users;

public class Creep extends User {

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public void setAccountCredentials(AccountCredentials accountCredentials) {
        super.setAccountCredentials(accountCredentials);
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    public Creep(int id, AccountCredentials accountCredentials, String firstName, String lastName) {
        super(id, accountCredentials, firstName, lastName);
    }

}
