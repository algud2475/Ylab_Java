package io.ylab.intensive.lesson04.persistentmap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать
 */
public class PersistentMapImpl implements PersistentMap {

    private DataSource dataSource;
    private String name;

    public PersistentMapImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init(String name) {
        this.name = name;
    }

    @Override
    public boolean containsKey(String key) throws SQLException {
        boolean ans = false;
        String getQuery = "SELECT KEY FROM persistent_map WHERE map_name = ? AND KEY = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ans = resultSet.getString("KEY").equals(key);
            }
        }
        return ans;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> ans = new ArrayList<>();
        String getQuery = "SELECT KEY FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, this.name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ans.add(resultSet.getString(1));
            }
        }
        return ans;
    }

    @Override
    public String get(String key) throws SQLException {
        String ans = null;
        String getQuery = "SELECT value FROM persistent_map WHERE map_name = ? AND  KEY = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            ans = resultSet.getString("value");
        }
        return ans;
    }

    @Override
    public void remove(String key) throws SQLException {
        String getQuery = "DELETE FROM persistent_map WHERE map_name = ? AND KEY = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        if (containsKey(key)) {
            remove(key);
        }
        String insertQuery = "INSERT INTO persistent_map (map_name, KEY, value) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void clear() throws SQLException {
        String getQuery = "DELETE FROM persistent_map WHERE map_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
            preparedStatement.setString(1, this.name);
            preparedStatement.executeUpdate();
        }
    }
}
