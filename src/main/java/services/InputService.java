package services;

import java.util.Scanner;

public class InputService {

    public static final Scanner scanner = new Scanner(System.in);

    public int getIntInput() {
        while (!scanner.hasNextInt()) {
            displayMessageWithLn("Wrong input. Enter a number, please");
            scanner.nextLine();
        }
        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    public String getStringInput() {
        return scanner.nextLine();
    }

    public void displayMessageWithLn(String text) {
        System.out.println(text);
    }

}
