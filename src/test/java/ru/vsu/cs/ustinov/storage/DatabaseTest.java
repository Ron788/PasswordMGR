package ru.vsu.cs.ustinov.storage;

import org.junit.jupiter.api.Test;
import ru.vsu.cs.ustinov.crypto.Crypto;

import javax.crypto.SecretKey;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    void WriteReadTest() {
        Database database = new Database("./testStorage");
        SecretKey secretKey = Crypto.getKeyFromPassword("It`s strong pass для теста!!!");

        Map<String, String> data = new HashMap<>();
        data.put("key", "value");
        data.put("key2", "value2");
        data.put("key3", "value3");
        data.put("key4", "value4");
        data.put("key5", "value5");

        database.write(secretKey, data);

        Map<String, String> result = database.read(secretKey);

        assertEquals(data, result);
    }
}