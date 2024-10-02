package ru.vsu.cs.ustinov.crypto;

import ru.vsu.cs.ustinov.Config;
import ru.vsu.cs.ustinov.storage.Storage;

import javax.crypto.SecretKey;
import java.io.Console;
import java.util.Scanner;

public class Password {
    private String password;

    public String getPassword() {
        return password;
    }

    public Password() {
        getUserPassword();
    }

    public boolean userLogin(){
        return checkValidUserInput();
    }

    void getUserPassword(){
        // Берем пароль у пользователя и сохраняем в поле
        this.password = Crypto.convertPassword(readPasswordFromUser());
    }

    boolean checkValidUserInput() {
        /*
        Пароль сохраненный хэшируем, сравниваем с записанным в файл loginInfo,
        если все сошлось, можно считать, что авторизация пользователя прошла успешно
         */

        SecretKey secretKey = Crypto.getKeyFromPassword(password);
        Storage storage = new Storage(Config.getStoragePath());



        return false;
    }

    char[] readPasswordFromUser() {
        /*
         Просто считываем пароль от пользователя,
         используем для возможности скрытого ввода
         (при вводе пароля он не будет в консоли отображаться)
        */
        Console console = System.console();

        char[] password;
        if (console == null) {
            // Но в идее скрытый ввод не поддерживается, поэтому читаем обычным образом
            System.out.print("Enter password: ");
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine().toCharArray();
        }else {
            password = console.readPassword("Enter password: ");
        }

        return password;
    }
}
