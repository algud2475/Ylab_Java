package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageFilter {
    @Autowired
    private DbClient dbClient;

    public String checkMessage(String input) {
        String[] temp = input.split("[ .,;?!$]");
        String output = input;
        for (String word : temp) {
            if (dbClient.checkWord(word)) {
                output = output.replaceAll(word, fixWord(word));
            }
        }
        return output;
    }

    private String fixWord(String input) {
        char[] output = input.toCharArray();
        for (int i = 1; i < output.length - 1; i++) {
            output[i] = '*';
        }
        return String.valueOf(output);
    }
}
