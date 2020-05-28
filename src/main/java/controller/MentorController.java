package controller;

import daos.artifact.ArtifactDAO;
import daos.artifact.ArtifactDAOImpl;
import daos.quest.QuestDAO;
import daos.quest.QuestDAOImpl;
import database.PostgreSQLJDBC;
import models.users.Codecooler;
import models.users.Mentor;

import javax.swing.text.View;
import java.util.List;
import java.util.Scanner;

public class MentorController {
    PostgreSQLJDBC postgreSQLJDBC;
    private QuestDAOImpl questDAO;
    private ArtifactDAOImpl artifactDAO;
    private View view;
    public List<Codecooler> codecoolers;

    public MentorController(){
        view = new View();
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
        artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);

    }
    public MenuMentor(){
        boolean isRunning = true;
        while (isRunning){
            view.clearScreen();
            view.displayMnetorMenu();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    break;

                case "2":
                    break;

                case "3":
                    break;

                case "4":
                    break;

                case "5":
                    break;

                case "6":
                    break;

                case "7":
                    break;

                case "8":
                    isRunning = false;
                    break;

                default:
                    break;

            }
        }
    }
}
