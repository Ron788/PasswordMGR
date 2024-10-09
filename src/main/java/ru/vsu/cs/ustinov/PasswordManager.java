package ru.vsu.cs.ustinov;

import ru.vsu.cs.ustinov.crypto.AuthService;
import java.util.Scanner;

public class PasswordManager {
    /*
    Пупупу, вроде готово.
    Старался комментировать большинство моментов -_-

    P.S.
    Cодержимое этого класса и класса Commands мне вообще не нравится
    + конфиги желательно подгружать из какого-нибудь файла
    но я и так затянул со сдачей =))))

    UPD: местами вроде лучше, но в целом оценить сложно
     */
    public static void main(String[] args) {
        System.out.println("== Password Manager ==");

        if (!checkRegister()){
            Commands.register();
        }

        System.out.println("Авторизация!");
        AuthService authService = new AuthService();
        if (!authService.checkUserLogin()){
            return;
        }

        System.out.println("Вы вошли в менеджер паролей!");
        System.out.println("\n -_- Просмотр доступных команд по команде help!");

        Commands commands = new Commands(authService.getSecretKey());

        //noinspection InfiniteLoopStatement
        while (true) {
            readCommand(commands);
        }
    }

    static void readCommand(Commands commands) {
        System.out.print("\n== Введи команду >> ");
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        switch (command.trim().toLowerCase()) {
            case "add":
                commands.add();
                break;
            case "view":
                commands.view();
                break;
            case "remove":
                commands.remove();
                break;
            case "list":
                commands.list();
                break;
            default:
                Commands.help();
                break;
        }

    }

    static boolean checkRegister(){
        if (AuthService.checkRegistration()){
            return true;
        }

        System.out.println("Для начала работы необходимо зарегистрироваться!");
        return false;
    }
}
