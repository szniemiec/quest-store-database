package models.users;

import enums.RoleEnum;

public class AccountCredentials {

    private String login;
    private String password;
    private String email;
    private RoleEnum roleEnum;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

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
