package ru.vsu.cs.ustinov;

import org.json.simple.JSONObject;

import java.io.Console;
import java.util.Objects;
import java.util.Scanner;

public class PasswordManager {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        if (args.length == 0 || Objects.equals(args[0], "help")) {
            helpCommand();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", readPassword());

        System.out.println(jsonObject.toJSONString());
    }

    private static String readPassword(){
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

        return new String(password);
    }

    private static void helpCommand(){
        System.out.println("""
                Usage command <data>
                
                Available commands:
                    help
                    init
                    add <login>
                    get <login>
                    remove <login>
                    list
                """);
    }
}
