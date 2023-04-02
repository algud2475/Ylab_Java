package io.ylab.intensive.lesson05.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.components.DbClient;
import io.ylab.intensive.lesson05.eventsourcing.components.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonApiImpl implements PersonApi {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private RabbitSender rabbitSender;
    @Autowired
    private DbClient dbClient;

    @Override
    public void deletePerson(Long personId) {
        if (!(findPerson(personId) == null)) {
            String getQuery = "DELETE FROM person WHERE person_id = " + personId;
            rabbitSender.sendMessageToRMQ(getQuery);
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
        rabbitSender.sendMessageToRMQ(insertQuery);
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
