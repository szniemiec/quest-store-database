package view;

import models.Artifact;
import models.Quest;

import java.util.List;
import java.util.Scanner;

public class View {
    Scanner scanner = new Scanner(System.in);
    public final String NAME = "Please provide name: ";
    public final String DESCRIPTION = "Provide short description: ";
    public final String CATEGORY = "Provide category: \n" +
            "1 - EASY\n2 - MEDIUM\n3 - HARD\n4-Shirts\n";
    public final String REWARD = "Reward amount:";

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public int getIntInput() {
        while (!scanner.hasNextInt()) {
            displayMessageWithLn("Wrong input. Enter a number, please");
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void displayMessageWithLn(String text) {
        System.out.println(text);
    }

    public void displayMentorMenu() {
        System.out.println("       Menu - MENTOR" +
                "\n\n  1. Add new quest" +
                "\n  2. Add new artifact" +
                "\n  3. Edit quest" +
                "\n  4. Edit artifact" +
                "\n  5. Accept student's quest" +
                "\n  6. Accept student's artifact" +
                "\n  7. Check student's valet" +
                "\n 8. Exit");
    }

    public void HandleMenuEdit() {
        System.out.println("        Editor MENU" +
                "\n\n 1. Edit" +
                "\n 2. Delete" +
                "\n 3. Exit");
    }

    public void printHeaderQuest() {
        System.out.println(String.format("%s", "--------------------------------------------------------------------"));
        System.out.println(String.format("%-2s %-2s %-30s %-2s %-5s %-2s %-5s %-2s %-6s", "ID", "|", "NAME", "|", "DESCRIPTION", "|", "REWARD", "|", "CATEGORY   |"));
        System.out.println(String.format("%s", "--------------------------------------------------------------------"));
    }

    public void printQuestTable(List<Quest> quests) {
        printHeaderQuest();
        for (Quest quest : quests) {
            System.out.println(String.format("%-2s %-2s %-30s %-2s %-5s %-2s %-6s %-2s %-2s %-5.2f %-1s", quest.getId(), "|",
                    quest.getName(), "|",
                    quest.getDescription(), "|",
                    quest.getReward(), "|", "*",
                    quest.getQuestCategoryEnum(), "|"));
        }
        System.out.println(String.format("%s", "--------------------------------------------------------------------"));
    }

    public void printHeaderArtifact() {
        System.out.println(String.format("%s", "--------------------------------------------------------------------"));
        System.out.println(String.format("%-2s %-2s %-30s %-2s %-5s %-2s %-5s %-2s", "ID", "|", "TITLE", "|", "DESCRIPTION", "|", "REWARD", "|", "CATEGORY   |"));
        System.out.println(String.format("%s", "--------------------------------------------------------------------"));
    }
    public void printArtifactTable(List<Artifact> artifacts) {
        printHeaderArtifact();
        for (Artifact artifact : artifacts) {
            System.out.println(String.format("%-2s %-2s %-30s %-2s %-5s %-2s %-6s %-2s %-2s %-5.2f %-1s", artifact.getId(), "|",
                    artifact.getTitle(), "|",
                    artifact.getDescription(), "|",
                    artifact.getPrice(), "|", "*",
                    "|"));
        }
        System.out.println(String.format("%s", "--------------------------------------------------------------------"));
    }
}