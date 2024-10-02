package ru.vsu.cs.ustinov;

import java.io.Console;
import java.util.Scanner;

public class Commands {
    static void add(){
        //TODO: Сделать сюда логику нада
    }
    static void remove(){
        //TODO: И сюда логику нада
    }
    static void list(){
        //TODO: И тут логика нужна
    }
    static void view(){
        //TODO: Пупупу тут тоже пусто
    }
    static void register(){
        //TODO: Мдооо даже здесь ничего
    }
    static void help(){
        //TODO: Ну вот с этим же никаких проблем не должно быть
    }

    static char[] readPassword() {
        // Используем для возможности скрытого ввода
        // (при вводе пароля он не будет в консоли отображаться)
        Console console = System.console();

        char[] password;
        if (console == null) {
            // Но в идее это не поддерживается, поэтому читаем обычным образом
            System.out.print("Enter password: ");
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine().toCharArray();
        }else {
            password = console.readPassword("Enter password: ");
        }

        return password;
    }
}
