package ru.vsu.cs.ustinov;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @SuppressWarnings("unchecked")
    @Test
    void WriteRead() throws IOException, ParseException {
        Database db = new Database();

        JSONObject obj = new JSONObject();
        obj.put("name", "test");
        obj.put("age", 20);
        obj.put("gender", "male");

        JSONArray arr = new JSONArray();
        arr.add("test");

        obj.put("data", arr);

        db.write(obj);

        JSONObject obj2 = db.read();

        assertEquals(obj.toJSONString(), obj2.toJSONString());
    }

}