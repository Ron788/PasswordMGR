package ru.vsu.cs.ustinov;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.ustinov.utils.Database;
import ru.vsu.cs.ustinov.utils.EncryptionService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

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

    @Test
    void WriteReadData() throws NoSuchAlgorithmException, IOException, ParseException, InvalidKeySpecException {
        Database db = new Database();

        String key = EncryptionService.keyToString(EncryptionService.getKeyFromPassword("123321"));
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("name", "test");
        dataMap.put("age", "age");
        dataMap.put("gender", "male");

        Database.Data data1 = new Database.Data(key, dataMap);

        db.writeData(data1);
        Database.Data data2 = db.readData();

        assertEquals(data1.key(), data2.key());

        Map<String, String> dataMap2 = data2.values();

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            assertEquals(entry.getValue(), dataMap2.get(entry.getKey()));
        }


    }

}