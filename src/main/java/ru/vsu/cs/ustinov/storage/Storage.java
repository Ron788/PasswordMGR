package ru.vsu.cs.ustinov.storage;

import java.io.*;
import java.nio.charset.StandardCharsets;

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

    public boolean hasFile(String fileName){
        File file = new File(directoryPath + File.separator + fileName);
        return file.exists();
    }

    public String getFileContent(String fileName){
        File file = new File(directoryPath + File.separator + fileName);

        StringBuilder result = new StringBuilder();
        try (Reader reader = new FileReader(file)) {
            char[] buffer = new char[10];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                result.append(new String(buffer, 0, charsRead));
            }

            return result.toString();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public boolean checkNonEmptyFile(String fileName){
        File file = new File(directoryPath + File.separator + fileName);
        if (!file.exists()){
            return false;
        }

        try (Reader reader = new FileReader(file)){
            if (reader.toString().isEmpty()){
                return false;
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return true;
    }

    public void writeFile(String fileName, String content){
        File file = new File(directoryPath + File.separator + fileName);

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)){
            writer.write(content);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
