package io.ylab.intensive.lesson05.messagefilter;

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

    public void sendMessageToRMQ(String message, String queueName) {
        if (message != null) {
            try (Connection connection = connectionFactory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
                channel.queueDeclare(queueName, true, false, false, null);
                channel.queueBind(queueName, exchangeName, queueName);
                channel.basicPublish(exchangeName, queueName, null, message.getBytes());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
