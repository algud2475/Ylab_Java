package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.SQLException;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        DataSource dataSource = initDb();
        ConnectionFactory connectionFactory = initMQ();
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        PersonApiImpl personApi = new PersonApiImpl(dataSource, connectionFactory);
        personApi.savePerson(1l, "Mad", "Max", "Two");
        personApi.savePerson(2l, "Terminator", "Be", "Back");
        Person person = personApi.findPerson(1l);
        System.out.println(person.getName()); //Mad
        System.out.println(personApi.findAll().get(1).getName()); //Terminator
        personApi.deletePerson(1l); //удаление
        personApi.deletePerson(1l); //сообщение что запись не найдена
        System.out.println(personApi.findPerson(1l) == null); //true
        personApi.savePerson(2l, "Fly", "Flew", "Flown");
        System.out.println(personApi.findAll().get(0).getName()); //Fly
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
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
