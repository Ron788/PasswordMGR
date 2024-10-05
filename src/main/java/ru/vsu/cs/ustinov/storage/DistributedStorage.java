package ru.vsu.cs.ustinov.storage;

import ru.vsu.cs.ustinov.Config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistributedStorage extends Storage {
    /*
    Распределенное хранилище данных.

    Не знаю возможно не стоило бы делать этот класс наследником от Storage...
     */

    // Кол-во символов в файле одном
    private static final int BLOCK_SIZE = 128;

    public DistributedStorage(String directoryPath) {
        super(directoryPath);
    }

    public void writeData(String data) {
        /*
        Записываем данные в файлы по BLOCK_SIZE символов в каждом.
         */
        clearStorage();

        String fileName = Config.getDataFilesName();
        String fileExtension = Config.getDataFilesExtension();

        int blockCount = (int) Math.ceil(data.length() / (double) BLOCK_SIZE);
        for (int i = 0; i < blockCount; i++) {
            int start = i * BLOCK_SIZE;
            int end = Math.min(start + BLOCK_SIZE, data.length());
            String block = data.substring(start, end);

            File blockFile = new File(directoryPath, fileName + i + fileExtension);
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(blockFile), StandardCharsets.UTF_8)) {
                writer.write(block);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public String readFile() {
        /*
        Читаем все сохраненное и возвращаем одной строкой.
         */
        List<File> files = getFilesList();
        StringBuilder result = new StringBuilder();

        for (File file : files) {
            try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                char[] buffer = new char[BLOCK_SIZE];
                int charsRead;
                while ((charsRead = reader.read(buffer)) != -1) {
                    result.append(new String(buffer, 0, charsRead));
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return result.toString();
    }

    private List<File> getFilesList() {
        /*
        Получаем все файлы в которые уже что-то записано
         */
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((dir1, name) -> name.startsWith(Config.getDataFilesName()));
        if (files == null) {
            return new ArrayList<>();
        }

        List<File> fileList = new ArrayList<>(Arrays.asList(files));
        fileList.sort((f1, f2) -> {
            int index1 = Integer.parseInt(f1.getName().split("_")[1].split("\\.")[0]);
            int index2 = Integer.parseInt(f2.getName().split("_")[1].split("\\.")[0]);
            return Integer.compare(index1, index2);
        });

        return fileList;
    }

    private void clearStorage() {
        /*
        Удаляем все сохраненные данные (перед записью)
         */
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((dir1, name) -> name.startsWith(Config.getDataFilesName()));
        if (files != null) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

    }
}
