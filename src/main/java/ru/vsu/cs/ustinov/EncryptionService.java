package ru.vsu.cs.ustinov;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionService {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;

    private static final int IV_SIZE = 16;
    private static final byte[] IV = new byte[IV_SIZE];

    public SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        return keyGen.generateKey();
    }

    public SecretKey getKeyFromPassword(String password) {
        byte[] key = password.getBytes();
        return new SecretKeySpec(key, 0, 16, ALGORITHM);
    }

    public String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public String decrypt(String encryptedData, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);

        return new String(decryptedData);
    }

    public String keyToString(SecretKey key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public SecretKey stringToKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }
}
