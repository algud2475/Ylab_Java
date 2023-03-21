package filesort;

import java.io.*;
import java.util.List;

public class Utils {
    public static final int BUFFER_SIZE = 50000000;
    public static final int MAX_ARRAY_SIZE = 10;
    //Возможно применение динамического размера массива с приблизительным вычислением в зависимости от ресурсов JVM
    //public static final int MAX_ARRAY_SIZE = getMaxArrayMemory()/2;
    public static String inputFileName = "inputFile.txt";
    public static String outputFileName = "outputFile.txt";
    public static String tmpDirName = "tmp/";
    private static File tmpDir;

    public static void deletePrevTestFiles() {
        File inputFile = new File(inputFileName);
        File outputFile = new File(outputFileName);
        if (inputFile.exists()) {
            inputFile.delete();
        }
        if (outputFile.exists()) {
            outputFile.delete();
        }
    }

    public static void createTempFilesFolder() {
        tmpDir = new File(tmpDirName);
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
    }

    public static void deleteTempFiles() {
        File[] listOfTempFiles = tmpDir.listFiles();
        for (File file : listOfTempFiles) {
            file.delete();
        }
        tmpDir.delete();
    }

    public static File writeSortedArrayToFile(String name, List<Long> array) throws IOException {
        File file = new File(tmpDirName + name);
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Long i : array) {
                pw.println(i);
            }
            pw.flush();
        }
        return file;
    }

    public static void writeToFile(File inputFile, File outputFile) throws IOException {
        try (BufferedReader br1 = new BufferedReader(new FileReader(inputFile), BUFFER_SIZE);
             PrintWriter pw = new PrintWriter(outputFile)) {
            String line;
            while ((line = br1.readLine()) != null) {
                pw.println(line);
            }
            pw.flush();
        }
    }

    public static int getMaxArrayMemory() {
        int size = Integer.MAX_VALUE - 8;
        while (size > 1) {
            try {
                long[] arr = new long[size];
                return size;
            } catch (Throwable e) {
                size = size / 2;
            }
        }
        return size;
    }

}
