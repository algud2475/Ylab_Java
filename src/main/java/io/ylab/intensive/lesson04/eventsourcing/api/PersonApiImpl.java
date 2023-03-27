package io.ylab.intensive.lesson04.eventsourcing.api;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
    private DataSource dataSource;
    private ConnectionFactory connectionFactory;
    private String exchangeName = "exc";
    private String queueName = "queue";

    public PersonApiImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deletePerson(Long personId) {
        if (!(findPerson(personId) == null)) {
            String getQuery = "DELETE FROM person WHERE person_id = " + personId;
            try (com.rabbitmq.client.Connection connectionMQ = connectionFactory.newConnection();
                 Channel channel = connectionMQ.createChannel()) {
                channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
                channel.queueDeclare(queueName, true, false, false, null);
                channel.queueBind(queueName, exchangeName, "key");
                channel.basicPublish(exchangeName, "key", null, getQuery.getBytes());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Пользователь с id " + personId + " не найден. Команда удаления не выполнена");
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        String insertQuery = null;
        if (!(findPerson(personId) == null)) {
            insertQuery = String.format("UPDATE person SET first_name = '%s', last_name = '%s', middle_name = '%s' WHERE person_id = '%d'",
                    firstName, lastName, middleName, personId);
        } else {
            insertQuery = String.format("INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (%d, '%s', '%s', '%s')",
                    personId, firstName, lastName, middleName);
        }
        try (com.rabbitmq.client.Connection connectionMQ = connectionFactory.newConnection();
             Channel channel = connectionMQ.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, "key");
            channel.basicPublish(exchangeName, "key", null, insertQuery.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Person findPerson(Long personId) {
        Person person = null;
        String getQuery = "SELECT * FROM person WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("person_id");
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                person = new Person(id, name, lastName, middleName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return person;
    }

    @Override
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        String getQuery = "SELECT * FROM person";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getQuery);
            while (resultSet.next()) {
                Long id = resultSet.getLong("person_id");
                String name = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                persons.add(new Person(id, name, lastName, middleName));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return persons;
    }
}
