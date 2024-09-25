package ru.vsu.cs.ustinov.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для хранения и считывания данных, работает с JSONObject
 */
public class Database {

    public record Data(String key, Map<String, String> values) { }

    private static final String FILE_PATH = "./storage/";

    public void write(JSONObject jsonObject) throws IOException {
        new StorageService(FILE_PATH).write(jsonObject.toJSONString());
    }

    public JSONObject read() throws IOException, ParseException {
        String data = new StorageService(FILE_PATH).read();

        return (JSONObject) new JSONParser().parse(data);
    }

    @SuppressWarnings({"unchecked", "MismatchedQueryAndUpdateOfCollection"})
    public void writeData(Data data) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", data.key());

        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, String> entry : data.values().entrySet()) {
            JSONObject item = new JSONObject();
            item.put("login", entry.getKey());
            item.put("password", entry.getValue());

            jsonArray.add(item);
        }
        jsonObject.put("values", jsonArray);

        write(jsonObject);
    }

    public Data readData() throws IOException, ParseException {
        JSONObject jsonObject = read();

        String key = jsonObject.get("key").toString();
        JSONArray jsonArray = (JSONArray) jsonObject.get("values");
        Map<String, String> values = new HashMap<>();
        for (Object o : jsonArray) {
            JSONObject item = (JSONObject) o;
            values.put(item.get("login").toString(), item.get("password").toString());
        }
        return new Data(key, values);
    }
}
