package models.users;

import enums.ModuleEnum;
import models.Purse;

public class Codecooler extends User {

    private ModuleEnum moduleEnum;
    private Purse purse;



    public Codecooler(int id, AccountCredentials accountCredentials, String firstName, String lastName, ModuleEnum moduleEnum, Purse purse) {
        super(id, accountCredentials, firstName, lastName);
        this.moduleEnum = moduleEnum;
        this.purse = purse;
    }
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
    public ModuleEnum getModule() {
        return moduleEnum;
    }

    public Purse getPurse() {
        return purse;
    }

}
