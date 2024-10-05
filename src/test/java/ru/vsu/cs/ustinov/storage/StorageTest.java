package ru.vsu.cs.ustinov.storage;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @Test
    void WriteReadTest() {
        Storage storage = new Storage("./testStorage");

        String text = "Гостиная Анны Павловны начала понемногу наполняться. Приехала высшая знать Петербурга, люди самые разнородные по возрастам и характерам, но одинаковые по обществу, в каком все жили; приехала дочь князя Василия, красавица Элен, заехавшая за отцом, чтобы с ним вместе ехать на праздник посланника. Она была в шифре и бальном платье. Приехала и известная, как la femme la plus séduisante de Pétersbourg 1, молодая, маленькая княгиня Болконская, прошлую зиму вышедшая замуж и теперь не выезжавшая в большой свет по причине своей беременности, но ездившая еще на небольшие вечера. Приехал князь Ипполит, сын князя Василия, с Мортемаром, которого он представил; приехал и аббат Морио и многие другие.\n" +
                "— Вы не видали еще, — или: — вы не знакомы с ma tante?2 — говорила Анна Павловна приезжавшим гостям и весьма серьезно подводила их к маленькой старушке в высоких бантах, выплывшей из другой комнаты, как скоро стали приезжать гости, называла их по имени, медленно переводя глаза с гостя на ma tante, и потом отходила.";

        storage.writeFile("test.txt", text);

        assertEquals(text, storage.getFileContent("test.txt"));
    }

    @Test
    void checkNonEmptyFileTest(){
        Storage storage = new Storage("./testStorage");

        File file = new File("./testStorage/test.txt");
        if (file.exists()){
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }

        assertFalse(storage.checkNonEmptyFile("test.txt"));

        storage.writeFile("testFile.txt", "123");

        assertTrue(storage.checkNonEmptyFile("testFile.txt"));
    }
}