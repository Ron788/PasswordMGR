package ru.vsu.cs.ustinov;

import ru.vsu.cs.ustinov.crypto.Password;
import ru.vsu.cs.ustinov.storage.Database;

import java.util.Map;
import java.util.Scanner;

public class Commands {
    static void add(){
        // В принципе все эти методы однотипные, поэтому комменты будут только тут

        // Авторизация пользователя, сделал под это отдельный класс
        Password password = new Password();
        if (!password.checkUserLogin()){ // Туда же вынесена вся логика по вводу пароля и оттуда же будет ответ, если пароль неверный
            return;
        }
        String[] data = new String[2];

        // Считываем логин и пароль понятно
        System.out.print("Вводи логин чтоб добавить новую запись >> ");
        Scanner scanner = new Scanner(System.in);
        data[0] = scanner.nextLine();

        System.out.print("Вводи пароль теперь >> ");
        data[1] = scanner.nextLine();

        // А тут уже магия...
        // Ну если опустить ее, что эта мапа по сути все логины и пароли
        Map<String, String> dataMap = Database.read(password.getSecretKey());

        // Мы туда добавляем новые данные
        dataMap.put(data[0], data[1]);

        // И теперь сохраняем все
        Database.write(password.getSecretKey(), dataMap);

        System.out.println("Success wrode!");
    }
    static void remove(){
        Password password = new Password();
        if (!password.checkUserLogin()){
            return;
        }
        Map<String, String> dataMap = Database.read(password.getSecretKey());

        System.out.print("Вводи логин чтоб по нему удалить запись >> ");
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();

        if (dataMap.get(data) == null){
            System.out.println("Нет там такого!");
        }

        dataMap.remove(data);
        Database.write(password.getSecretKey(), dataMap);
        System.out.println("Success wrode!");

    }
    static void list(){
        Password password = new Password();
        if (!password.checkUserLogin()){
            return;
        }
        Map<String, String> dataMap = Database.read(password.getSecretKey());
        System.out.println("Логины в базе:");
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            System.out.println(entry.getKey());
        }
    }
    static void view(){
        Password password = new Password();
        if (!password.checkUserLogin()){
            return;
        }

        Map<String, String> dataMap = Database.read(password.getSecretKey());

        System.out.print("Вводи логин чтоб по нему узнать пароль >> ");
        Scanner scanner = new Scanner(System.in);
        String data = scanner.nextLine();

        String pass = dataMap.get(data);
        if (pass == null){
            System.out.println("Не найдено!!!");
            return;
        }

        System.out.println("Пароль >> " + pass);

    }
    static void register(){
        if (Password.checkRegistration()) {
            System.out.println("Уже зарегистрирован же...");
            return;
        }
        System.out.println("Регистрация!");
        Password password = new Password();
        if (!password.registration()){
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
                register - делается один раз, задается пароль для авторизации
                """);
    }
}
