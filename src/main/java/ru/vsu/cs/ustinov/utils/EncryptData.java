package ru.vsu.cs.ustinov.utils;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

public class EncryptData {
    public static Database.Data encrypt(Database.Data data, SecretKey secretKey) {
        Database.Data newData = new Database.Data(data.key(), new HashMap<>());
        for (Map.Entry<String, String> entry: data.values().entrySet()) {
            try {
                newData.values().put(entry.getKey(), EncryptionService.encrypt(entry.getValue(), secretKey));
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return newData;
    }

    public static Database.Data decrypt(Database.Data data, SecretKey secretKey) {
        Database.Data newData = new Database.Data(data.key(), new HashMap<>());
        for (Map.Entry<String, String> entry: data.values().entrySet()) {
            try {
                newData.values().put(entry.getKey(), EncryptionService.decrypt(entry.getValue(), secretKey));
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return newData;
    }
}
