package io.ylab.intensive.lesson05.eventsourcing.components;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {
    @Autowired
    private ConnectionFactory connectionFactory;
    private String exchangeName = "exc";
    private String queueName = "queue";

    public void sendMessageToRMQ(String message) {
        if (message != null) {
            try (Connection connection = connectionFactory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
                channel.queueDeclare(queueName, true, false, false, null);
                channel.queueBind(queueName, exchangeName, "key");
                channel.basicPublish(exchangeName, "key", null, message.getBytes());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
