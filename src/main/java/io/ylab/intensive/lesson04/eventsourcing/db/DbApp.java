package io.ylab.intensive.lesson04.eventsourcing.db;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.sql.DataSource;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;

public class DbApp {
    public static void main(String[] args) throws Exception {
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();
        // тут пишем создание и запуск приложения работы с БД
        String queueName = "queue";
        try (Connection connectionMQ = connectionFactory.newConnection();
             Channel channel = connectionMQ.createChannel();
             java.sql.Connection connectionDB = dataSource.getConnection();
             Statement statement = connectionDB.createStatement()) {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(queueName, true);
                if (message == null) {

                } else {
                    String received = new String(message.getBody());
                    System.out.println(getCurrentLocalDateTimeStamp() + ": " + received);
                    statement.executeUpdate(received);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }
}
