package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.components.DbClient;
import io.ylab.intensive.lesson05.eventsourcing.components.RabbitReceiver;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        // тут пишем создание и запуск приложения работы с БД
        RabbitReceiver rabbitReceiver = applicationContext.getBean(RabbitReceiver.class);
        DbClient dbClient = applicationContext.getBean(DbClient.class);
        while (!Thread.currentThread().isInterrupted()) {
            dbClient.sendToDB(rabbitReceiver.getMessageFromRMQ());
        }
    }
}
