package io.ylab.intensive.lesson05.eventsourcing.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DbClient {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private RabbitReceiver rabbitReceiver;
    private String queueName = "queue";

    public void sendToDB(String message) {
        if (message != null) {
            try (Connection connection = dataSource.getConnection();
                 Statement statement = connection.createStatement()) {
                System.out.println(getCurrentLocalDateTimeStamp() + ": " + message);
                statement.executeUpdate(message);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
