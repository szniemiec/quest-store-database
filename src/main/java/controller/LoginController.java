package controller;

import daos.UserDAO;
import database.PostgreSQLJDBC;
import enums.RoleEnum;
import models.users.AccountCredentials;
import models.users.Codecooler;
import models.users.User;
import services.InputService;
import view.View;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    UserDAO userDAO;
    View view;
    InputService inputService;
    AccountCredentials accountCredentials;
    boolean isRunning;

    public LoginController(PostgreSQLJDBC postgreSQLJDBC) {
        this.userDAO = new UserDAO(postgreSQLJDBC);
        isRunning = true;
        this.inputService =  new InputService();
        this.view = new View();
    }

    public void startLogin() throws Exception {
        view.clearScreen();
        loginProcess();
        menuSwitch();

    }

    public User loginProcess() throws IOException, SQLException {
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
