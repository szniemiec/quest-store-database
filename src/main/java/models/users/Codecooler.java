package models.users;

import models.Purse;

public class Codecooler extends User {

    private Purse purse;

    public Codecooler(int id, AccountCredentials accountCredentials, String firstName, String lastName, Purse purse) {
        super(id, accountCredentials, firstName, lastName);
        this.purse = purse;
    }

}
