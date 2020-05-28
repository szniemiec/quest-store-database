package controller;

import daos.artifact.ArtifactDAOImpl;
import daos.quest.QuestDAOImpl;
import database.PostgreSQLJDBC;
import models.Artifact;
import models.Quest;
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
//                    createNewArtifact();
                    break;
                case "3":
                    HandleMenuEditQuest();
                    break;
                case "4":
                    HandleMenuEditArtifact();
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

    public void HandleMenuEditQuest() {
        boolean isRunning = true;
        while (isRunning) {
            view.clearScreen();
            view.HandleMenuEdit();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    editQuest();
                    break;
                case "2":
                    removeQuest();
                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void HandleMenuEditArtifact() {
        boolean isRunning = true;
        while (isRunning) {
            view.clearScreen();
            view.HandleMenuEdit();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    editArtifact();
                    break;
                case "2":
                    removeArtifact();
                    break;
                case "3":
                    isRunning = false;
                    break;
                default:
                    break;
            }
        }
    }

    private void createNewQuest() {
        System.out.println(view.NAME);
        String name = view.getInput();
        System.out.println(view.DESCRIPTION);
        int description = view.getIntInput();
        System.out.println(view.CATEGORY);
        String category = view.getInput();
        System.out.println(view.REWARD);
        int reward = view.getIntInput();

//        Quest quest = new Quest();

//        questDAO.addQuest(quest);
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
    }

//    private void createNewArtifact() {
//        System.out.println(view.NAME);
//        String name = view.getInput();
//        System.out.println(view.DESCRIPTION);
//        int description = view.getIntInput();
//        System.out.println(view.REWARD);
//        int reward = view.getIntInput();
//
//        Artifact artifact = new Artifact()
//
//        artifactDAO.addArtifact(artifact);
//        artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);
//    }

    public void removeQuest() {
        view.displayMessageWithLn("Please enter the ID of the Quest to remove:");
        int questId = view.getIntInput();
        questDAO.deleteQuest(questId);
        System.out.println("ID: " + questId + ". The product has been removed");
    }

    public void removeArtifact() {
        view.displayMessageWithLn("Please, enter the ID of the Artifact to remove:");
        int ArtifactId = view.getIntInput();
        artifactDAO.deleteArtifact(ArtifactId);
        System.out.println("ID: " + ArtifactId + ". The product has been removed");
    }

    public void editQuest() {

    }

    public void editArtifact() {

    }
}
