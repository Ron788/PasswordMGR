package ru.vsu.cs.ustinov;

import ru.vsu.cs.ustinov.crypto.Password;

import java.io.Console;
import java.util.Scanner;

public class Commands {
    static void add(){
        //TODO: Сделать сюда логику нада
        Password password = new Password();
        if (!password.userLogin()){
            System.out.println("пупупупу");
            return;
        }

        System.out.println("NORMASSS");

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
