package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class ApiApp {
    public static void main(String[] args) throws SQLException {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        personApi.savePerson(1l, "Mad", "Max", "Two");
        personApi.savePerson(2l, "Terminator", "Be", "Back");
        personApi.savePerson(3l, "John", "Wick", "Incredible");
        //Mad, Terminator, John
        if (!personApi.findAll().isEmpty()) {
            for (Person person : personApi.findAll()) {
                System.out.println(person.getName());
            }
        }
        personApi.deletePerson(1l); //удаление
        System.out.println(personApi.findPerson(1l) == null);
        //false - после внедрения Spring данные в БД не успевают обновляться достаточно быстро
        personApi.deletePerson(1l); //сообщение что запись не найдена
        personApi.savePerson(2l, "Fly", "Flew", "Flown");
        if (!personApi.findAll().isEmpty()) {
            for (Person person : personApi.findAll()) {
                System.out.println(person.getName());
            }
        }
        //John, Fly
    }
}
