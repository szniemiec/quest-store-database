package enums;

public enum ModuleEnum {

    PROG_BASICS(1),
    JAVA_OOP(2),
    WEB(3),
    ADVANCED(4);

    private int moduleId;

    ModuleEnum(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getModuleId() {
        return this.moduleId;
    }

}
