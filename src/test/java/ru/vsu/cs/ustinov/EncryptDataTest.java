package ru.vsu.cs.ustinov;

import org.junit.jupiter.api.Test;
import ru.vsu.cs.ustinov.utils.Database;
import ru.vsu.cs.ustinov.utils.EncryptData;
import ru.vsu.cs.ustinov.utils.EncryptionService;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EncryptDataTest {
    @Test
    void encryptDecrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Database.Data data = new Database.Data("123", new HashMap<>());
        data.values().put("1", "a");
        data.values().put("2", "b");
        data.values().put("3", "c");

        SecretKey secretKey = EncryptionService.getKeyFromPassword("123312");

        Database.Data encryptedData = EncryptData.encrypt(data, secretKey);
        Database.Data decryptedData = EncryptData.decrypt(encryptedData, secretKey);

        assertEquals(data, decryptedData);
    }

}