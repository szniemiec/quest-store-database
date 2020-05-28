package controller;

import daos.artifact.ArtifactDAOImpl;
import daos.quest.QuestDAOImpl;
import database.PostgreSQLJDBC;
import models.users.Codecooler;

import view.View;

import java.util.List;
import java.util.Scanner;

public class MentorController {

    private PostgreSQLJDBC postgreSQLJDBC;
    private QuestDAOImpl questDAO;
    private ArtifactDAOImpl artifactDAO;
    public View view;
    public List<Codecooler> codecoolers;

    public MentorController() {
        view = new View();
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
        artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);
    }

    public void MenuMentor() {
        boolean isRunning = true;
        while (isRunning) {
            view.clearScreen();
            view.displayMentorMenu();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    createNewQuest();
                    break;
                case "2":
                    createNewArtifact();
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

    public void createNewQuest() {
        System.out.println(view.NAME);
        String name = view.getInput();
        System.out.println(view.DESCRIPTION);
        int description = view.getIntInput();
        System.out.println(view.CATEGORY);
        String category = view.getInput();
        System.out.println(view.REWARD);
        int reward = view.getIntInput();

        questDAO.AddQuest(name, description, category, reward);
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
    }

    public void createNewArtifact() {
        System.out.println(view.NAME);
        String name = view.getInput();
        System.out.println(view.DESCRIPTION);
        int description = view.getIntInput();
        System.out.println(view.REWARD);
        int reward = view.getIntInput();

        artifactDAO.AddArtifact(name, description, reward);
        artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);
    }
}
