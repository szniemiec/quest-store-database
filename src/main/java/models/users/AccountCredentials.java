package models.users;

import enums.RoleEnum;

import java.util.Objects;

public class AccountCredentials {

    private String login;
    private String password;
    private String email;
    private RoleEnum roleEnum;
//
    public AccountCredentials(String login, String password, String email, RoleEnum roleEnum) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roleEnum = roleEnum;
    }

    public AccountCredentials() {

    }

    public AccountCredentials setLogin(String login) {
        this.login = login;
        return this;
    }

    public AccountCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public AccountCredentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public AccountCredentials setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountCredentials that = (AccountCredentials) o;
        return login.equals(that.login) &&
                password.equals(that.password) &&
                email.equals(that.email) &&
                roleEnum == that.roleEnum;
    }
}
