package models.users;

import enums.ModuleEnum;
import models.Purse;

public class Codecooler extends User {

    private ModuleEnum moduleEnum;
    private Purse purse;

    public Codecooler(int id, AccountCredentials accountCredentials, String firstName, String lastName) {
        super(id, accountCredentials, firstName, lastName);
    }

    public Codecooler(AccountCredentials accountCredentials, String firstName, String lastName) {
        super(accountCredentials, firstName, lastName);
    }

    public Codecooler(int id, AccountCredentials accountCredentials, String firstName, String lastName, ModuleEnum moduleEnum, Purse purse) {
        super(id, accountCredentials, firstName, lastName);
        this.moduleEnum = moduleEnum;
        this.purse = purse;
    }
//
//    public Codecooler() {
//        super(id, accountCredentials, firstname, lastName);
//    }

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
    public ModuleEnum getModule() {
        return moduleEnum;
    }

    public Purse getPurse() {
        return purse;
    }

    public Codecooler setModuleEnum(ModuleEnum moduleEnum) {
        this.moduleEnum = moduleEnum;
        return this;
    }
}
