package ru.vsu.cs.ustinov.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для распределенного хранилища информации
 * По сути просто разбивает информацию на куски и записывает все в разные файлы в одной папке
 */
public class StorageService {

    private static final int BLOCK_SIZE = 128; // кол-во символов в одном файле
    private final String directoryPath;

    public StorageService(String directoryPath) {
        this.directoryPath = directoryPath;
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }

    }

    public void write(String data) throws IOException {
        clearStorage();

        int blockCount = (int) Math.ceil(data.length() / (double) BLOCK_SIZE);
        for (int i = 0; i < blockCount; i++) {
            int start = i * BLOCK_SIZE;
            int end = Math.min(start + BLOCK_SIZE, data.length());
            String block = data.substring(start, end);

            File blockFile = new File(directoryPath, "block_" + i + ".dat");
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(blockFile), StandardCharsets.UTF_8)) {
                writer.write(block);
            }

        }
    }

    public String read() throws IOException {
        List<File> files = getFilesList();
        StringBuilder result = new StringBuilder();

        for (File file : files) {
            try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                char[] buffer = new char[BLOCK_SIZE];
                int charsRead;
                while ((charsRead = reader.read(buffer)) != -1) {
                    result.append(new String(buffer, 0, charsRead));
                }
            }
        }

        return result.toString();
    }

    private void clearStorage() {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((dir1, name) -> name.startsWith("block_"));
        if (files != null) {
            for (File file : files) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }

    }

    private List<File> getFilesList() {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((dir1, name) -> name.startsWith("block_"));
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


}
