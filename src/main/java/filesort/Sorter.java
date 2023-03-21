package filesort;

import java.io.*;
import java.util.*;

public class Sorter {
    private static Deque<File> dequeOfTempFiles = new ArrayDeque<>();
    private static int numberOfFiles;

    public static void splitFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file), Utils.BUFFER_SIZE)) {
            numberOfFiles = 0;
            String i = br.readLine();
            while (i != null) {
                int count = 0;
                List<Long> valueArray = new ArrayList<>();
                while (i != null && count < Utils.MAX_ARRAY_SIZE) {
                    valueArray.add(Long.parseLong(i));
                    i = br.readLine();
                    count++;
                }
                Collections.sort(valueArray);
                numberOfFiles++;
                dequeOfTempFiles.addLast(Utils.writeSortedArrayToFile("f" + numberOfFiles, valueArray));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void mergeTwoFiles(File fi1, File fi2, File fo) {
        try (BufferedReader br1 = new BufferedReader(new FileReader(fi1), Utils.BUFFER_SIZE);
             BufferedReader br2 = new BufferedReader(new FileReader(fi2), Utils.BUFFER_SIZE);
             PrintWriter pw = new PrintWriter(fo)) {
            String s1 = br1.readLine();
            String s2 = br2.readLine();
            long l1;
            long l2;
            while (s1 != null || s2 != null) {
                while (s1 != null && s2 != null) {
                    if ((l1 = Long.parseLong(s1)) < (l2 = Long.parseLong(s2))) {
                        pw.println(l1);
                        s1 = br1.readLine();
                    } else {
                        pw.println(l2);
                        s2 = br2.readLine();
                    }
                }
                while (s1 == null && s2 != null) {
                    pw.println(s2);
                    s2 = br2.readLine();
                }
                while (s1 != null && s2 == null) {
                    pw.println(s1);
                    s1 = br1.readLine();
                }
            }
            dequeOfTempFiles.addLast(fo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public File sortFile(File inputFile) throws IOException {
        File sortedFile = new File(Utils.outputFileName);
        Utils.createTempFilesFolder();
        splitFile(inputFile);
        File preSortedFile = dequeOfTempFiles.getFirst();
        while (dequeOfTempFiles.size() > 1) {
            numberOfFiles++;
            preSortedFile = new File(Utils.tmpDirName + "f" + numberOfFiles);
            File f1 = dequeOfTempFiles.pollFirst();
            File f2 = dequeOfTempFiles.pollFirst();
            mergeTwoFiles(f1, f2, preSortedFile);
            f1.delete();
            f2.delete();
        }
        Utils.writeToFile(preSortedFile, sortedFile);
        Utils.deleteTempFiles();
        return sortedFile;
    }
}
