package ru.vsu.cs.ustinov;

public class Config {
    /*
    Конфиги...
    (по поводу содержания отзывался уже в комментах в классе PasswordManager)
     */
    private static final String STORAGE_PATH = "./storage";

    private static final String LOGIN_FILE = "login.dat";

    private static final String DATA_FILES_NAME = "data_";
    private static final String DATA_FILES_EXTENSION = ".dat";

    public static String getStoragePath() {
        return STORAGE_PATH;
    }

    public static String getLoginFile() {
        return LOGIN_FILE;
    }

    public static String getDataFilesName() {
        return DATA_FILES_NAME;
    }

    public static String getDataFilesExtension() {
        return DATA_FILES_EXTENSION;
    }
}
