package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.List;

@Component
public class DbClient {
    @Autowired
    private DataSource dataSource;
    @Autowired
    BadWordsParser badWordsParser;

    public void createDBTable() {
        String ddl = "create table tableofbadwords (bad_word varchar)";
        try {
            DbUtil.applyDdl(ddl, dataSource);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean isDBTableExist() {
        boolean ans = false;
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "tableofbadwords", null);
            ans = resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }

    public void clearDBTable() {
        String query = "DELETE FROM tableofbadwords";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertWordsToDBTable(File file) {
        if (!isDBTableExist()) {
            createDBTable();
        }
        clearDBTable();
        List<String> badWords = badWordsParser.parseBadWords(file);
        String query = "INSERT INTO tableofbadwords (bad_word) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (String badWord : badWords) {
                preparedStatement.setString(1, badWord);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkWord(String word) {
        boolean ans = false;
        String query = "SELECT bad_word FROM tableofbadwords WHERE bad_word = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, word.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            ans = resultSet.next();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }
}
