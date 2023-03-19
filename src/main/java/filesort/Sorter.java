package filesort;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Sorter {
    private static final int MAX_CHUNK_SIZE = 1000000; // максимальный размер чанка

    public File sortFile(File dataFile) throws IOException {
        List<File> chunkFiles = splitFileIntoChunks(dataFile);
        List<FileInputStream> inputStreams = createInputStreams(chunkFiles);
        PriorityQueue<NumberWrapper> priorityQueue = createPriorityQueue(inputStreams);
        File sortedFile = mergeInputStreams(priorityQueue);
        deleteTempFiles(chunkFiles);
        return sortedFile;
    }

    // Разбиваем файл на части и возвращаем список временных файлов-чанков
    private List<File> splitFileIntoChunks(File dataFile) throws IOException {
        List<File> chunkFiles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile), MAX_CHUNK_SIZE)) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<Long> chunk = new ArrayList<>();
                int count = 0;
                while (line != null && count < MAX_CHUNK_SIZE) {
                    chunk.add(Long.parseLong(line));
                    line = reader.readLine();
                    count++;
                }
                Collections.sort(chunk);
                File chunkFile = File.createTempFile("chunk", ".tmp");
                try (PrintWriter writer = new PrintWriter(chunkFile)) {
                    for (long number : chunk) {
                        writer.println(number);
                    }
                }
                chunkFiles.add(chunkFile);
            }
        }
        return chunkFiles;
    }

    // Создаем список FileInputStream из списка временных файлов-чанков
    private List<FileInputStream> createInputStreams(List<File> chunkFiles) throws FileNotFoundException {
        List<FileInputStream> inputStreams = new ArrayList<>();
        for (File chunkFile : chunkFiles) {
            inputStreams.add(new FileInputStream(chunkFile));
        }
        return inputStreams;
    }

    // Создаем приоритетную очередь из FileInputStream с использованием
    // вспомогательного класса NumberWrapper, который помимо самого числа
    // хранит информацию о текущем файле-источнике и BufferedReader для чтения чисел
    private PriorityQueue<NumberWrapper> createPriorityQueue(List<FileInputStream> inputStreams) throws IOException {
        PriorityQueue<NumberWrapper> priorityQueue = new PriorityQueue<>();
        for (FileInputStream is : inputStreams) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            NumberWrapper numberWrapper = new NumberWrapper(Long.parseLong(reader.readLine()), reader, is);
            priorityQueue.add(numberWrapper);
        }
        return priorityQueue;
    }

    // Сливаем числа из приоритетной очереди в отсортированный файл и возвращаем его
    private File mergeInputStreams(PriorityQueue<NumberWrapper> priorityQueue) throws IOException {
        File sortedFile = File.createTempFile("sorted", ".tmp");
        try (PrintWriter writer = new PrintWriter(sortedFile)) {
            while (!priorityQueue.isEmpty()) {
                NumberWrapper numberWrapper = priorityQueue.poll();
                writer.println(numberWrapper.getNumber());
                String line = numberWrapper.getReader().readLine();
                if (line != null) {
                    NumberWrapper newNumberWrapper = new NumberWrapper(Long.parseLong(line),
                            numberWrapper.getReader(), numberWrapper.getInputStream());
                    priorityQueue.add(newNumberWrapper);
                } else {
                    numberWrapper.close();
                }
            }
        }
        return sortedFile;
    }

    // Удаляем временные файлы чанков
    private void deleteTempFiles(List<File> chunkFiles) {
        for (File chunkFile : chunkFiles) {
            chunkFile.delete();
        }
    }
}
