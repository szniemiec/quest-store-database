package models.users;

import models.Wallet;

public class Codecooler extends User {

    private Wallet wallet;

    public Codecooler(int id, AccountCredentials accountCredentials, String firstName, String lastName, Wallet wallet) {
        super(id, accountCredentials, firstName, lastName);
        this.wallet = wallet;
    }

}
