package ru.vsu.cs.ustinov;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    @Test
    void encryptDecrypt() throws Exception {
        EncryptionService encryptionService = new EncryptionService();

        String data = "test data to encrypt";

        SecretKey secretKey = encryptionService.generateSecretKey();

        String encryptedData = encryptionService.encrypt(data, secretKey);
        String decryptedData = encryptionService.decrypt(encryptedData, secretKey);

        assertEquals(data, decryptedData);
    }

    @Test
    void stringKeyConvert() throws Exception {
        EncryptionService encryptionService = new EncryptionService();

        SecretKey secretKey = encryptionService.generateSecretKey();

        String secretKeyString = encryptionService.keyToString(secretKey);

        assertEquals(secretKey, encryptionService.stringToKey(secretKeyString));
    }
}