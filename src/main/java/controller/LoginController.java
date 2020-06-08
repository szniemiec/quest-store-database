package controller;

import daos.UserDAO;
import enums.RoleEnum;
import models.users.AccountCredentials;
import models.users.Codecooler;
import models.users.User;
import services.InputService;
import view.View;

import java.io.IOException;

public class LoginController {
    UserDAO userDAO;
    View view = new View();
    InputService inputService = new InputService();
    AccountCredentials accountCredentials;
    boolean isRunning;

    public LoginController() {
        UserDAO userDAO = new UserDAO();
        isRunning = true;
        this.inputService = inputService;
        this.accountCredentials = accountCredentials;
        this.view = view;
    }

    public void startLogin() throws Exception {
        view.clearScreen();
        loginProcess();
        menuSwitch();

    }

    public User loginProcess() throws IOException {
        String login = getLoginFromUser();
        String password = getPasswordFromUser();
        User loggingUser = userDAO.getLoggedUser(login, password);
        return loggingUser;
    }

    private String getLoginFromUser() {
        return inputService.stringWithMessage("Insert login: ");
    }

    private String getPasswordFromUser() {
        return inputService.stringWithMessage("Insert password: ");
    }

    public void menuSwitch() throws Exception {
        String login = getLoginFromUser();
        String password = getPasswordFromUser();
        User loggingUser = userDAO.getLoggedUser(login, password);
//        RoleEnum roleEnum = accountCredentials.getRoleEnum();
        RoleEnum roleEnum = loggingUser.getAccountCredentials().getRoleEnum();
        switch (roleEnum) {
            case CREEP:
//                CreepController creepController = new CreepController();
//                creepController.menuCreep();
                break;
            case MENTOR:
                MentorController mentorController = new MentorController();
                mentorController.MenuMentor();
                break;
            case CODECOOLER:
//                CodecoolerController codecoolerController = new CodecoolerController();
//                codecoolerController.menuCodecooler();
                break;
        }
    }
}
