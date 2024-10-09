package ru.vsu.cs.ustinov.storage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Storage {
    /*
    Класс для работы с файлами напрямую
     */
    protected final String directoryPath;

    public Storage(String directoryPath) {
        this.directoryPath = directoryPath;
        checkDir();

    }

    void checkDir(){
        /*
        Проверяем существование папки и создаем если не существует
         */
        File dir = new File(directoryPath);
        if(!dir.exists()){
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
    }

    public String getFileContent(String fileName){
        /*
        Получаем данные из какого-либо файла
         */
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
        /*
        Проверяем, что файл не пустой
         */
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
        /*
        Записываем в конкретный файл
         */
        File file = new File(directoryPath + File.separator + fileName);

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)){
            writer.write(content);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
