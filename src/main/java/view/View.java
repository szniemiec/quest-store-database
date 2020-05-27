package view;

import java.util.Scanner;

public class View {
    Scanner scanner = new Scanner(System.in);

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
}