package ru.vsu.cs.ustinov.storage;

import java.io.File;

public class Storage {
    private final String directoryPath;

    public Storage(String directoryPath) {
        this.directoryPath = directoryPath;
        checkDir();

    }

    void checkDir(){
        File dir = new File(directoryPath);
        if(!dir.exists()){
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
    }
}
