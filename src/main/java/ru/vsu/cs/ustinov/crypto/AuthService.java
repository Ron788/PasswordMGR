package ru.vsu.cs.ustinov.crypto;

import ru.vsu.cs.ustinov.Config;
import ru.vsu.cs.ustinov.storage.Storage;

import javax.crypto.SecretKey;
import java.io.Console;
import java.util.Scanner;

public class AuthService {
    /*
    Класс для авторизации пользователя.
    В целом этот класс тоже теперь отношу к тем, которые мне не нравятся =D
     */
    private final String password;
    // Исходный пароль и секретный ключ на основе его и используется для шифрования данных
    private final SecretKey secretKey;

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public AuthService() {
        /*
        Берем пароль у пользователя и сохраняем
        */
        this.password = readPasswordFromUser();
        this.secretKey = Crypto.getKeyFromPassword(password);
    }

    String readPasswordFromUser() {
        /*
         Просто считываем пароль от пользователя,
         используем для возможности скрытого ввода
         (при вводе пароля он не будет в консоли отображаться)
        */
        String printMessage = "Введите пароль >> ";

        Console console = System.console();

        String password;
        if (console == null) {
            // Но в идее скрытый ввод не поддерживается, поэтому читаем обычным образом
            System.out.print(printMessage);
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine().trim();
        }else {
            password = Crypto.convertPassword(console.readPassword(printMessage));
        }

        return password;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkUserLogin(){
        /*
        Проверяем успешна ли авторизация пользователя
         */
        if (checkValidPassword()){
            return true;
        }

        System.out.println("== Ошибка! Неверный пароль!");
        return false;
    }

    boolean checkValidPassword() {
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

    public static boolean checkRegistration(){
        /*
        Проверяем существует ли (и не пустой ли он) файл с данными на вход
        По-хорошему нужно больше проверок, но пока сойдет думаю
         */
        return new Storage(Config.getStoragePath()).checkNonEmptyFile(Config.getLoginFile());
    }

    public boolean registerUser(){
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
