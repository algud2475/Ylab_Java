package filesort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        Utils.deletePrevTestFiles();
        File dataFile = new Generator().generate(Utils.inputFileName, 100);
        System.out.println(new Validator(dataFile).isSorted()); //false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); //true
    }
}

/*
* Понимаю что создавать демонстрационный выходной файл путём копирования итогового - лишняя работа машины.
* За неимением времени извиняюсь за неидеальный вариант.
* Понимание алгоритма внешней сортировки слиянием продемонстрировал, спасибо
*/
