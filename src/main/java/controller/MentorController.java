package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import daos.artifact.ArtifactDAOImpl;
import daos.mentor.MentorDAOImpl;
import daos.quest.QuestDAO;
import daos.quest.QuestDAOImpl;
import database.PostgreSQLJDBC;
import enums.QuestCategoryEnum;
import models.Artifact;
import models.Quest;
import models.users.Codecooler;
import models.users.Mentor;
import services.InputService;
import view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MentorController implements HttpHandler {
    public InputService inputService;
    private Artifact artifact;
    private Quest quest;
    private PostgreSQLJDBC postgreSQLJDBC;
    private MentorDAOImpl mentorDAO;
    private QuestDAOImpl questDAO;
    private ArtifactDAOImpl artifactDAO;
    private boolean isEditing;
    public View view;
    public List<Codecooler> codecoolers;

    public MentorController(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
        view = new View();
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
        mentorDAO = new MentorDAOImpl(postgreSQLJDBC);
        artifactDAO = new ArtifactDAOImpl(postgreSQLJDBC);
        inputService = new InputService();
        artifact = new Artifact();
        quest = new Quest();
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

    public void HandleMenuEditArtifact() {
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
        System.out.println(view.NAME);
        String name = inputService.getStringInput();
        System.out.println(view.DESCRIPTION);
        String description = inputService.getStringInput();
        System.out.println(view.REWARD);
        int reward = inputService.getIntInput();
        System.out.println(view.CATEGORY);
        int categoryId = inputService.getIntInput();
        questDAO = new QuestDAOImpl(postgreSQLJDBC);
        questDAO.addQuest(new Quest(name, description, reward, categoryIdToEnum(categoryId)));

    }

    private void createNewArtifact() {
        System.out.println(view.NAME);
        String title = inputService.getStringInput();
        System.out.println(view.DESCRIPTION);
        String description = inputService.getStringInput();
        System.out.println(view.COST);
        int cost = inputService.getIntInput();

        artifactDAO.addArtifact(new Artifact(title, description, cost));
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
        QuestDAO questDao = new QuestDAOImpl(postgreSQLJDBC);
        Quest editedQuest = questDao.getQuest(questId);
        String newValue;
        int newValueInt;
        int userChoice = inputService.getIntInput();
        switch (userChoice) {
            case 1:
                newValue = getUserInput();
                questDao.editQuest(editedQuest.setName(newValue));
                isEditing = false;
                break;
            case 2:
                newValue = getUserInput();
                questDao.editQuest(editedQuest.setDescription(newValue));
                isEditing = false;
                break;
            case 3:
                QuestCategoryEnum value;
                newValueInt = getUserIntInput();
                value = categoryIdToEnum(newValueInt);
                questDao.editQuest(editedQuest.setCategory(value));
                isEditing = false;
                break;
            case 4:
                newValueInt = getUserIntInput();
                questDao.editQuest(editedQuest.setReward(newValueInt));
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

    public int getUserIntInput() {
        System.out.println("Please, type a new value:");
        return inputService.getIntInput();
    }

    public void editArtifactDetailsMenu() {
        System.out.println("\nEDIT ARTIFACT DATA PANEL");
        inputService.displayMessageWithLn("Enter a artifact Id:");
        int artifactId = inputService.getIntInput();
        isEditing = true;
        inputService.displayMessageWithLn("What do you want to edit?\n1. Name\n2. Description\n 3. Reward\n4. Exit");
        while (isEditing) {
            editArtifact(artifactId);
        }
    }

    public void editArtifact(int artifactId) {
        ArtifactDAOImpl artifactDao = new ArtifactDAOImpl(postgreSQLJDBC);
        String newValue;
        int userChoice = inputService.getIntInput();
        switch (userChoice) {
            case 1:
                newValue = getUserInput();
                artifactDao.editArtifact(artifact.setTitle(newValue));
                isEditing = false;
                break;
            case 2:
                newValue = getUserInput();
                artifactDao.editArtifact(artifact.setDescription(newValue));
                isEditing = false;
                break;
            case 3:
                int newValueInt = Integer.valueOf(getUserInput());
                artifactDao.editArtifact(artifact.setCost(newValueInt));
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
        switch (id) {
            case 1 -> questCategoryEnum = QuestCategoryEnum.EASY;
            case 2 -> questCategoryEnum = QuestCategoryEnum.MEDIUM;
            case 3 -> questCategoryEnum = QuestCategoryEnum.HARD;
            case 4 -> questCategoryEnum = QuestCategoryEnum.SHIRTS;
            default -> throw new Exception("Wrong category id");
        }
        return questCategoryEnum;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";

        try {
            List<Mentor> mentors = this.mentorDAO.getMentors();
            //tworzenie jsona
            // response to to co sie poazuje na localost w przegladarce
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.writeValueAsString(mentors);
            System.out.println(response);

            httpExchange.getResponseHeaders().put("Content-Type", Collections.singletonList("application/json"));
            //CORS policy * - zezwolenie na komunikacje z kazdym frontem
            httpExchange.getResponseHeaders().put("Access-Control-Allow-Origin", Collections.singletonList("*"));
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
        } catch (Exception e) {
            httpExchange.sendResponseHeaders(404, response.length());
        }
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
