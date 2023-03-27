package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        // Написать код демонстрации работы
        persistentMap.init("test");
        persistentMap.put("1", "a");
        persistentMap.put("2", "b");
        System.out.println(persistentMap.containsKey("1")); // true
        System.out.println(persistentMap.containsKey("3")); // false
        System.out.println(persistentMap.getKeys()); // [1, 2]
        System.out.println(persistentMap.get("1")); // a
        persistentMap.put("2", "c");
        System.out.println(persistentMap.get("2")); // c
        persistentMap.remove("1");
        System.out.println(persistentMap.getKeys()); // [2]
        persistentMap.clear();
        System.out.println(persistentMap.getKeys()); // []
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
