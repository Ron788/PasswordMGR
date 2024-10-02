package ru.vsu.cs.ustinov;

import java.io.Console;
import java.io.File;
import java.util.Scanner;

public class PasswordManager {

    public static void main(String[] args) {
        System.out.println("== Password Manager ==");

        System.out.println("""
                Доступные команды:
                    help
                    register
                    add
                    view
                    list
                    remove
                """);

        //noinspection InfiniteLoopStatement
        while (true) {
            readCommand();
        }
    }

    static void readCommand(){
        System.out.print("\n== Введи команду >> ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        if (command.equals("register")) {
            Commands.register();
            return;
        }else if (command.equals("help")) {
            Commands.help();
            return;
        }

        if (!checkRegister()){
            return;
        }

        switch (command) {
            case "add":
                Commands.add();
                break;
            case "view":
                Commands.view();
                break;
            case "remove":
                Commands.remove();
                break;
            case "list":
                Commands.list();
                break;
            default:
                Commands.help();
                break;
        }

    }

    static boolean checkRegister(){
        if (checkStorage()){
            return true;
        }

        System.out.println("Для работы необходимо зарегистрироваться!\n- Используй команду register -_-");
        return false;
    }

    static boolean checkStorage(){
        //TODO: Изменить логику проверки
        File dir = new File(Config.getStoragePath());
        return dir.exists();
    }
}
