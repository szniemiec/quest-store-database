package enums;

public enum RoleEnum {

    CREEP(1),
    MENTOR(2),
    CODECOOLER(3);

    private int roleId;

    RoleEnum(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return this.roleId;
    }

}
