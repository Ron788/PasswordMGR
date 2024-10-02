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
