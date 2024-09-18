package ru.vsu.cs.ustinov;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Database {
    private static final String FILE_PATH = "./storage/";

    public void write(JSONObject jsonObject) throws IOException {
        new StorageService(FILE_PATH).write(jsonObject.toJSONString());
    }

    public JSONObject read() throws IOException, ParseException {
        String data = new StorageService(FILE_PATH).read();

        return (JSONObject) new JSONParser().parse(data);
    }


}
