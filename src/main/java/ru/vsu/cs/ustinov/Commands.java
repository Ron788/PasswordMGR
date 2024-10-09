package ru.vsu.cs.ustinov;

import ru.vsu.cs.ustinov.crypto.AuthService;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.Scanner;


public class Commands {
    /*
    Здесь логика команд находится
    (по поводу содержания отзывался уже в комментах в классе PasswordManager)
     */
    
    private final SecretKey secretKey;

    public Commands(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    void add(){

        String[] dataArr = new String[2];

        // Считываем логин и пароль понятно
        System.out.print("Вводи логин чтоб добавить новую запись >> ");
        Scanner scanner = new Scanner(System.in);
        dataArr[0] = scanner.nextLine().trim();

        System.out.print("Вводи пароль теперь >> ");
        dataArr[1] = scanner.nextLine().trim();

        // Немного другая магия теперь, чуть по приятнее
        Data data = new Data(Config.getStoragePath(), secretKey);
        data.put(dataArr[0], dataArr[1]);
        data.pushData();

        System.out.println("Success wrode!");
    }

    void remove(){

        Data data = new Data(Config.getStoragePath(), secretKey);

        System.out.print("Вводи логин чтоб по нему удалить запись >> ");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine().trim();

        if (data.get(login) == null){
            System.out.println("Нет там такого!");
        }

        data.remove(login);
        data.pushData();

        System.out.println("Success wrode!");

    }

    void list(){
        Data data = new Data(Config.getStoragePath(), secretKey);

        if (data.isEmpty()){
            System.out.println("Пусто!");
            return;
        }

        System.out.println("Логины в базе:");
        for (Map.Entry<String, String> entry : data.getData().entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    void view(){
        Data data = new Data(Config.getStoragePath(), secretKey);

        System.out.print("Вводи логин чтоб по нему узнать пароль >> ");
        Scanner scanner = new Scanner(System.in);
        String log = scanner.nextLine().trim();

        String pass = data.get(log);
        if (pass == null){
            System.out.println("Не найдено!!!");
            return;
        }

        System.out.println("Пароль >> " + pass);

    }

    static void register(){
        if (AuthService.checkRegistration()) {
            System.out.println("Уже зарегистрирован же...");
            return;
        }
        AuthService authService = new AuthService();
        if (!authService.registerUser()){
            System.out.println("Что-то пошло не так...");
            return;
        }

        System.out.println("Успешно!");
    }

    static void help(){
        System.out.println("""
                Использование:
                
                add - команда для добавления пароля в базу паролей
                remove - команда для удаления пароля из базы
                view - просмотр пароля по логину
                list - просмотр всех добавленных логинов
                """);
    }
}
