package controller;

import daos.UserDAO;
import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.User;
import services.InputService;
import view.View;

import java.sql.SQLException;

public class LoginController {
    PostgreSQLJDBC postgreSQLJDBC;
    UserDAO userDAO;
    View view;
    InputService inputService;
    boolean isRunning;

    public LoginController(PostgreSQLJDBC postgreSQLJDBC) {
       this.postgreSQLJDBC = postgreSQLJDBC;
        this.userDAO = new UserDAO(postgreSQLJDBC);
        this.view = new View();
        this.inputService = new InputService();
        isRunning = true;
    }

    public void startLogin() throws Exception {
        view.clearScreen();
        User loggingUser = loginProcess();
        menuSwitch(loggingUser);
    }

    public User loginProcess() throws SQLException {
        String login = getLoginFromUser();
        String password = getPasswordFromUser();
        User loggingUser = userDAO.getLoggedUser(login, password);
        return loggingUser;
    }

    public void menuSwitch(User loggingUser) throws Exception {
        RoleEnum roleEnum = loggingUser.getAccountCredentials().getRoleEnum();
        switch (roleEnum) {
            case CREEP:
//                CreepController creepController = new CreepController();
//                creepController.menuCreep();
                break;
            case MENTOR:
                MentorController mentorController = new MentorController(postgreSQLJDBC);
                mentorController.MenuMentor();
                break;
            case CODECOOLER:
//                CodecoolerController codecoolerController = new CodecoolerController();
//                codecoolerController.menuCodecooler();
                break;
        }
    }

    private String getLoginFromUser() {
        return inputService.stringWithMessage("Insert login: ");
    }

    private String getPasswordFromUser() {
        return inputService.stringWithMessage("Insert password: ");
    }
}