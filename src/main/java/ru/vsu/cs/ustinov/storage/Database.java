package ru.vsu.cs.ustinov.storage;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ru.vsu.cs.ustinov.crypto.Crypto;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

public class Database {
    /*
    Класс для работы с данными.
    Тут данные всегда представляются в виде Map<String, String> логин-пароль,
    для записи конвертируется в JSON. JSON в строку, строка шифруется.
     */

    private final String storagePath;

    public Database(String storagePath) {
        this.storagePath = storagePath;
    }

    public Map<String, String> read(SecretKey secretKey) {
        /*
        Читаем данные из хранилища.
        Принимается секретный ключ, чтобы дешифровать данные сразу.
         */

        // Класс для работы с хранилищем
        DistributedStorage dStorage = new DistributedStorage(storagePath);
        // Читаем все данные и расшифровываем
        String encryptedData = Crypto.decrypt(dStorage.readFile(), secretKey);

        Map<String, String> data = new HashMap<>();
        // На случай если данных нет
        if (encryptedData.isEmpty()) {
            return data;
        }

        // Парсим жсон
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(encryptedData);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Разбираем жсон
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        for (Object o : jsonArray){
            JSONObject item = (JSONObject) o;
            data.put(item.get("login").toString(), item.get("pass").toString());
        }

        return data;
    }

    @SuppressWarnings("unchecked")
    public void write(SecretKey secretKey, Map<String, String> data) {
        /*
        Записываем данные в хранилище
        Мапу конвертируем в JSON, JSON в строку, строку шифруем и записываем
         */
        DistributedStorage dStorage = new DistributedStorage(storagePath);

        // Основной объект
        JSONObject jsonObject = new JSONObject();
        // Массив объектов, где собственно и будут данные
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            // Пакуем пароли в объект
            JSONObject item = new JSONObject();
            item.put("login", entry.getKey());
            item.put("pass", entry.getValue());

            // Вставляем объект в массив объектов
            jsonArray.add(item);
        }

        // Ну и массив вставляем в объект
        jsonObject.put("data", jsonArray);

        // Шифруем данные по ключу и записываем это все
        dStorage.writeData(Crypto.encrypt(jsonObject.toJSONString(), secretKey));
    }
}
