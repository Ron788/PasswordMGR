package ru.vsu.cs.ustinov;

import java.io.Console;
import java.util.Scanner;

public class PasswordManager {

    public static void main(String[] args) {



        Console console = System.console();

        char[] password;
        if (console == null) {
            // Usually when running in ide, so it is better to run through the console (for Linux there is a run.sh file)

            System.out.println("No console available");
            System.out.print("Enter password: ");
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine().toCharArray();
        }else {
            password = console.readPassword("Enter password: ");
        }

        System.out.println(password);
    }
}
