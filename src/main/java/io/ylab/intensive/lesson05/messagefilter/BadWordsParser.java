package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BadWordsParser {
    public List<String> parseBadWords(File file) {
        List<String> badWords = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                badWords.add(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println("Oops! There are some troubles with a File!");
        }
        return badWords;
    }
}
