package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

public class MessageFilterApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        DbClient dbClient = applicationContext.getBean(DbClient.class);
        dbClient.insertWordsToDBTable(new File("badWords.txt"));
        RabbitReceiver rabbitReceiver = applicationContext.getBean(RabbitReceiver.class);
        RabbitSender rabbitSender = applicationContext.getBean(RabbitSender.class);
        MessageFilter messageFilter = applicationContext.getBean(MessageFilter.class);
        String testMessage = "Fuck you, уважаемый! Вы просто кончита! Почему? Потому что дрочить - это не мат, а искусство!";
        rabbitSender.sendMessageToRMQ(testMessage, "input");
        while (!Thread.currentThread().isInterrupted()) {
            String inMessage = rabbitReceiver.getMessageFromRMQ("input");
            if (inMessage != null) {
                rabbitSender.sendMessageToRMQ(messageFilter.checkMessage(inMessage), "output");
            }
            String outMessage = rabbitReceiver.getMessageFromRMQ("output");
            if (outMessage != null) {
                System.out.println(outMessage);
            }
        }
    }
}
