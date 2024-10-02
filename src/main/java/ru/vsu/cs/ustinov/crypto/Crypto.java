package ru.vsu.cs.ustinov.crypto;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

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
}
