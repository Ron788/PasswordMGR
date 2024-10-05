package ru.vsu.cs.ustinov.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class CryptoTest {

    @Test
    void EncryptDecrypt() {
        String text = "Hello World! Привет мир.";
        SecretKey secretKey = Crypto.getKeyFromPassword("auf ssssstrong passs");

        String encryptedText = Crypto.encrypt(text, secretKey);
        String decryptedText = Crypto.decrypt(encryptedText, secretKey);

        assertEquals(text, decryptedText);
    }
}