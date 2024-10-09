package ru.vsu.cs.ustinov;

import ru.vsu.cs.ustinov.storage.Database;

import javax.crypto.SecretKey;
import java.util.Map;

public class Data {
    /*
    Класс для непосредственной манипуляции с данными
    В целом у методов говорящие названия, наверное и не буду комментировать их...
    Ну и тесты тут ни к чему, просто обертка над классом Database и над методами мапы
     */

    private final String storagePath;
    private final SecretKey secretKey;

    private Map<String, String> data;

    public Data(String storagePath, SecretKey secretKey) {
        this.storagePath = storagePath;
        this.secretKey = secretKey;

        pullData();
    }

    public Map<String, String> getData() {
        return data;
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public void pullData(){
        Database db = new Database(storagePath);

        this.data = db.read(secretKey);
    }

    public void pushData(){
        Database db = new Database(storagePath);

        db.write(secretKey, data);
    }

    public void put(String login, String pass){
        data.put(login, pass);
    }

    public String get(String login){
        return data.get(login);
    }

    public void remove(String login){
        data.remove(login);
    }
}
