package ru.vsu.cs.ustinov.crypto;

import ru.vsu.cs.ustinov.Config;
import ru.vsu.cs.ustinov.storage.Storage;

import javax.crypto.SecretKey;
import java.io.Console;
import java.util.Scanner;

//TODO: Надо переименоваться бы этот класс и методы ммм
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
        /*
        Берем пароль у пользователя и сохраняем в поле
        */
        this.password = Crypto.convertPassword(readPasswordFromUser());
    }

    boolean checkValidUserInput() {
        /*
        Пароль сохраненный хэшируем, сравниваем с записанным в файл loginInfo,
        если все сошлось, можно считать, что авторизация пользователя прошла успешно
         */

        SecretKey secretKey = Crypto.getKeyFromPassword(password);
        Storage storage = new Storage(Config.getStoragePath());

        if (!checkRegistration()){
            return false;
        }

        String loginData = storage.getFileContent(Config.getLoginFile());

        return loginData.equals(Crypto.keyToString(secretKey));
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

    public static boolean checkRegistration(){
        /*
        Проверяем существует ли (и не пустой ли он) файл с данными на вход
        По-хорошему нужно больше проверок, но пока сойдет думаю
         */
        return new Storage(Config.getStoragePath()).checkNonEmptyFile(Config.getLoginFile());
    }

    public boolean registration(){
        //TODO: Переименовать бы
        /*
        Метод регистрирующий пользователя. Кэширует пароль и вписывает его в файл
         */
        if (checkRegistration()){
            return false;
        }

        SecretKey secretKey = Crypto.getKeyFromPassword(password);
        Storage storage = new Storage(Config.getStoragePath());

        storage.writeFile(Config.getLoginFile(), Crypto.keyToString(secretKey));

        return true;
    }
}
