package ru.vsu.cs.ustinov;

public class Config {
    private static final String STORAGE_PATH = "./storage";

    private static final String LOGIN_FILE = "login.dat";

    public static String getStoragePath() {
        return STORAGE_PATH;
    }

    public static String getLoginFile() {
        return LOGIN_FILE;
    }
}
