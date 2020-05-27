package models.users;

import enums.RoleEnum;

public class AccountCredentials {

    private String login;
    private String password;
    private String email;
    private RoleEnum roleEnum;

    public AccountCredentials(String login, String password, String email, RoleEnum roleEnum) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roleEnum = roleEnum;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

}
