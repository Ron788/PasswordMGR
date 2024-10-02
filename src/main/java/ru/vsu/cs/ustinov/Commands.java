package ru.vsu.cs.ustinov;

import java.io.Console;
import java.util.Scanner;

public class Commands {
    static void add(){

    }
    static void remove(){

    }
    static void list(){

    }
    static void view(){

    }
    static void register(){

    }
    static void help(){

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
