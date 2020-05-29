package controller;

import daos.artifact.ArtifactDAO;
import daos.artifact.ArtifactDAOImpl;
import daos.quest.QuestDAOImpl;
import database.PostgreSQLJDBC;
import enums.QuestCategoryEnum;
import models.Artifact;
import models.Quest;
import models.users.Codecooler;
import services.InputService;
import view.View;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MentorController {
    private Artifact artifact;
    private Quest quest;
    private PostgreSQLJDBC postgreSQLJDBC;
    private InputService inputService;
    private QuestDAOImpl questDAO;
    private ArtifactDAOImpl artifactDAO;
    private boolean isEditing;
    public View view;
    public List<Codecooler> codecoolers;

    public MentorController() {
        view = new View();
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
        artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);
        inputService = new InputService();
    }

    public void MenuMentor() throws Exception {
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

    public void HandleMenuEditQuest() throws Exception {
        boolean isRunning = true;
        while (isRunning) {
            view.clearScreen();
            view.HandleMenuEdit();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    editQuestDetailsMenu();
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

    public void HandleMenuEditArtifact() throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            view.clearScreen();
            view.HandleMenuEdit();
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    editArtifactDetailsMenu();
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

    private void createNewQuest() throws Exception {
        int id = 0;
        System.out.println(view.NAME);
        String name = inputService.getStringInput();
        System.out.println(view.DESCRIPTION);
        String description = inputService.getStringInput();
        System.out.println(view.REWARD);
        int reward = inputService.getIntInput();
        System.out.println(view.CATEGORY);
        int categoryId = inputService.getIntInput();

        questDAO.addQuest(new Quest( id, name, description, categoryIdToEnum(categoryId), reward));
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
    }

    private void createNewArtifact() {
        int id = 0;
        System.out.println(view.NAME);
        String title = inputService.getStringInput();
        System.out.println(view.DESCRIPTION);
        String description = inputService.getStringInput();
        System.out.println(view.COST);
        int cost = inputService.getIntInput();

        artifactDAO.addArtifact(new Artifact(id, title, description, cost));
        artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);
    }

    public void removeQuest() {
        inputService.displayMessageWithLn("Please enter the ID of the Quest to remove:");
        int questId = inputService.getIntInput();
        questDAO.deleteQuest(questId);
        System.out.println("ID: " + questId + ". The product has been removed");
    }

    public void removeArtifact() {
        inputService.displayMessageWithLn("Please, enter the ID of the Artifact to remove:");
        int ArtifactId = inputService.getIntInput();
        artifactDAO.deleteArtifact(ArtifactId);
        System.out.println("ID: " + ArtifactId + ". The product has been removed");
    }

    public void editQuestDetailsMenu() throws Exception {
        System.out.println("\nEDIT QUEST DATA PANEL");
        inputService.displayMessageWithLn("Enter a quest Id:");
        int questId = inputService.getIntInput();
        isEditing = true;
        inputService.displayMessageWithLn("What do you want to edit?\n1. Name\n2. Description\n3. Category\n" +
                "4. Reward\n5. Exit");
        while (isEditing) {
            editQuest(questId);
        }
    }

    public void editQuest(int questId) throws Exception {
        QuestDAOImpl questDao = new QuestDAOImpl(postgreSQLJDBC);
        Quest quest = questDAO.getQuest(questId);
        String newValue = getUserInput();
        int newValueInt = Integer.valueOf(newValue);
        int userChoice = inputService.getIntInput();
        switch (userChoice) {
            case 1:
                newValue = getUserInput();
//                questDao.editQuest(questId, "Name", newValue);
                quest.setName(newValue);
                questDAO.editQuest(quest);
                isEditing = false;
                break;
            case 2:
                newValue = getUserInput();
//                questDao.editQuest(questId, "Description", newValue);
                quest.setDescription(newValue);
                questDAO.editQuest(quest);
                isEditing = false;
                break;
            case 3:
                view.HandleMenuEditCategory();
//                newValue = getUserInput();
//                int newValueInt = Integer.valueOf(newValue);
                quest.setQuestCategoryEnum(categoryIdToEnum(newValueInt));
                questDao.editQuest(quest);
                isEditing = false;
                break;
            case 4:
//                newValue = getUserInput();
//                int newValueInt = Integer.valueOf(newValue);
//                questDao.editQuest(questId, "Reward", newValue);
                quest.setReward(newValueInt);
                questDAO.editQuest(quest);
                isEditing = false;
                break;
            case 5:
                isEditing = false;
                break;
            default:
                break;
        }
    }

    public String getUserInput() {
        System.out.println("Please, type a new value:");
        return inputService.getStringInput();
    }

    public void editArtifactDetailsMenu() throws SQLException {
        System.out.println("\nEDIT ARTIFACT DATA PANEL");
        inputService.displayMessageWithLn("Enter a artifact Id:");
        int artifactId = inputService.getIntInput();
        isEditing = true;
        inputService.displayMessageWithLn("What do you want to edit?\n1. Name\n2. Description\n 3. Reward\n4. Exit");
        while (isEditing) {
            editArtifact(artifactId);
        }
    }

    public void editArtifact(int artifactId) throws SQLException {
        Artifact artifact = artifactDAO.getArtifact(artifactId);
        ArtifactDAOImpl artifactDao = new ArtifactDAOImpl(postgreSQLJDBC);
        String newValue;
        int userChoice = inputService.getIntInput();
        switch (userChoice) {
            case 1:
                newValue = getUserInput();
//                questDao.editQuest(questId, "Name", newValue);
                artifact.setTitle(newValue);
                artifactDao.editArtifact(artifact);
                isEditing = false;
                break;
            case 2:
                newValue = getUserInput();
//                artifactDao.editArtifact(artifactId, "Description", newValue);
                artifact.setDescription(newValue);
                artifactDao.editArtifact(artifact);
                isEditing = false;
                break;
            case 3:
                newValue = getUserInput();
                int newValueInt = Integer.valueOf(newValue);
//                artifactDao.editArtifact(artifactId, "Cost", newValue);
                artifact.setPrice(newValueInt);
                artifactDao.editArtifact(artifact);
                isEditing = false;
                break;
            case 4:
                isEditing = false;
                break;
            default:
                break;
        }
    }

    private QuestCategoryEnum categoryIdToEnum(int id) throws Exception {
        QuestCategoryEnum questCategoryEnum;
        if (id == 1) {
            questCategoryEnum = QuestCategoryEnum.EASY;
        } else if (id == 2) {
            questCategoryEnum = QuestCategoryEnum.MEDIUM;
        } else if (id == 3) {
            questCategoryEnum = QuestCategoryEnum.HARD;
        } else {
            throw new Exception("Wrong category id");
        }
        return questCategoryEnum;
    }
}
