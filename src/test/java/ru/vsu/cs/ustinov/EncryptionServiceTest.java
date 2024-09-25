package ru.vsu.cs.ustinov;

import org.junit.jupiter.api.Test;
import ru.vsu.cs.ustinov.utils.EncryptionService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionServiceTest {

    @Test
    void encryptDecrypt() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        // тест того что зашифровав и расшифровав получим то же самое

        String data = "test data to encrypt";

        SecretKey secretKey = EncryptionService.getKeyFromPassword("123321");
        String encryptedData = EncryptionService.encrypt(data, secretKey);

        String decryptedData = EncryptionService.decrypt(encryptedData, secretKey);

        assertEquals(data, decryptedData);
    }

    @Test
    void stringKeyConvert() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // проверка конвертации ключа в строку и обратно

        SecretKey secretKey = EncryptionService.getKeyFromPassword("123321");

        String secretKeyString = EncryptionService.keyToString(secretKey);

        assertEquals(secretKey, EncryptionService.stringToKey(secretKeyString));
    }
}