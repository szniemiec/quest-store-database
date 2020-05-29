package view;

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
    public void HandleMenuEdit(){
        System.out.println("        Editor MENU" +
                        "\n\n 1. Edit" +
                        "\n 2. Delete" +
                        "\n 3. Exit");
    }
    public void HandleMenuEditCategory(){
        System.out.println("        Provide new category" +
                "\n\n 1. Easy" +
                "\n 2. Medium" +
                "\n 3. Hard");
    }
}
