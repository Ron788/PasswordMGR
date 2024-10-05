package ru.vsu.cs.ustinov.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

//TODO: pereimenovat bi
public class Crypto {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 256;

    public static String convertPassword(char[] password) {
        /*
         Кодируем введенный пароль в base64, получая строку большей длинны
         */
        byte[] bytes = new String(password).getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static SecretKey getKeyFromPassword(String password) {
        /*
        Генерируем секретный ключ на основе пароля
         */
        KeySpec spec = new PBEKeySpec(password.toCharArray(), new byte[16], 65536, KEY_SIZE);
        try{
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            return new SecretKeySpec(f.generateSecret(spec).getEncoded(), ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String keyToString(SecretKey key) {
        /*
        Конвертируем секретный ключ в строку
         */
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static SecretKey stringToKey(String key) {
        /*
        Конвертируем строку в секретный ключ
         */
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    public static String encrypt(String data, SecretKey secretKey) {
        /*
        Шифруем данные по ключу
         */
        try{
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String decrypt(String encryptedData, SecretKey secretKey) {
        /*
        Расшифровываем данные по ключу
         */
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            IvParameterSpec ivSpec = new IvParameterSpec(new byte[16]);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedData = cipher.doFinal(decodedData);

            return new String(decryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String hashString(String input) {
        /*
        Хэшируем строку
         */
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
