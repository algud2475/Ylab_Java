package io.ylab.intensive.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLQueryBuilderImpl implements SQLQueryBuilder {
    @Autowired
    private DataSource dataSource;
    //Изменить types на null для вывода ВООБЩЕ ВСЕХ таблиц из БД; либо на {"TABLE"} для вывода только рабочих таблиц
    private String[] types = {"TABLE"};

    @Override
    public String queryForTable(String tableName) throws SQLException {
        String ans = null;
        StringBuilder stringBuilder = new StringBuilder();
        try (Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.getMetaData().getColumns(null, null, tableName, null);
            while (resultSet.next()) {
                stringBuilder.append(resultSet.getString("COLUMN_NAME") + ", ");
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.insert(0, "SELECT ");
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), " FROM " + tableName);
            ans = stringBuilder.toString();
        }
        return ans;
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "%", types);
            while (resultSet.next()) {
                tables.add(resultSet.getString(3));
            }
        }
        return tables;
    }
}
