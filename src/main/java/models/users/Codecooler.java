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

    public ModuleEnum getModule() {
        return moduleEnum;
    }

    public Purse getPurse() {
        return purse;
    }

}
