package models.users;

import enums.Role;

public class AccountCredentials {

    private String login;
    private String password;
    private String email;
    private Role roleId;

    public AccountCredentials(String login, String password, String email, Role roleId) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }
}
