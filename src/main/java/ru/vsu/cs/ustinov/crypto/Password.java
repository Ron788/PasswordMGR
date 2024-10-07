package ru.vsu.cs.ustinov.crypto;

import ru.vsu.cs.ustinov.Config;
import ru.vsu.cs.ustinov.storage.Storage;

import javax.crypto.SecretKey;
import java.io.Console;
import java.util.Scanner;

//TODO: Надо переименоваться бы этот класс и методы ммм
public class Password {
    /*
    Класс для авторизации пользователя.
    В целом этот класс тоже теперь отношу к тем, которые мне не нравятся =D
     */
    private String password;
    // Исходный пароль и секретный ключ на основе его и используется для шифрования данных
    private SecretKey secretKey;

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public Password() {
        getUserPassword();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkUserLogin(){
        /*
        Проверяем успешна ли авторизация пользователя
         */
        if (checkValidUserInput()){
            return true;
        }

        System.out.println("Пароль то неверный!");
        return false;
    }

    void getUserPassword(){
        /*
        Берем пароль у пользователя и сохраняем
        */
        this.password = Crypto.convertPassword(readPasswordFromUser());
        secretKey = Crypto.getKeyFromPassword(password);
    }

    boolean checkValidUserInput() {
        /*
        Пароль сохраненный хэшируем, сравниваем с записанным в файл loginInfo,
        если все сошлось, можно считать, что авторизация пользователя прошла успешно
         */

        String hashPass = Crypto.hashString(password);
        Storage storage = new Storage(Config.getStoragePath());

        if (!checkRegistration()){
            return false;
        }

        String loginData = storage.getFileContent(Config.getLoginFile());

        return loginData.equals(hashPass);
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
            password = scanner.nextLine().trim().toCharArray();
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
        Метод регистрирующий пользователя. Хэширует пароль и вписывает его в файл
         */
        if (checkRegistration()){
            return false;
        }

        String hashPass = Crypto.hashString(password);
        Storage storage = new Storage(Config.getStoragePath());

        storage.writeFile(Config.getLoginFile(), hashPass);

        return true;
    }
}
