package ru.vsu.cs.ustinov;

import ru.vsu.cs.ustinov.crypto.Password;
import java.util.Scanner;

public class PasswordManager {
    /*
    Пупупу, вроде готово.
    Старался комментировать большинство моментов -_-

    P.S.
    Cодержимое этого класса и класса Commands мне вообще не нравится
    + конфиги желательно подгружать из какого-нибудь файла
    но я и так затянул со сдачей =))))

    UPD: вроде стало и лучше и не лучше одновременно...
     */
    public static void main(String[] args) {
        System.out.println("== Password Manager ==");

        System.out.println("""
                Доступные команды:
                    help
                    add
                    view
                    list
                    remove
                """);

        if (!checkRegister()){
            Commands.register();
        }

        System.out.println("Авторизация!");
        Password password = new Password();
        if (!password.checkUserLogin()){
            return;
        }
        System.out.println("Вы вошли в менеджер паролей!");

        Commands commands = new Commands(password.getSecretKey());

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
        if (Password.checkRegistration()){
            return true;
        }

        System.out.println("Для начала работы необходимо зарегистрироваться!");
        return false;
    }
}
