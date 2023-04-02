package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver {
    @Autowired
    private ConnectionFactory connectionFactory;

    public String getMessageFromRMQ(String queueName) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            GetResponse message = channel.basicGet(queueName, true);
            if (message == null) {
                return null;
            } else {
                return new String(message.getBody());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
