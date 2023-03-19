package filesort;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("FileSortIOFiles/inputData.txt", 100);
        System.out.println(new Validator(dataFile).isSorted()); //false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); //true

        //Можно записать на диск выходной файл чтобы воочию убедиться в результате
        //File sortedWrittenFile = new File("FileSortIOFiles/outputData.txt");
        //FileWriter.copyContent(sortedFile, sortedWrittenFile);
    }
}
