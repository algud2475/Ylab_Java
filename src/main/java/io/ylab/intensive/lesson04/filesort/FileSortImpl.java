package io.ylab.intensive.lesson04.filesort;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
    private DataSource dataSource;
    private final int BATCH_SIZE = 1000;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) throws IOException, SQLException {
        // ТУТ ПИШЕМ РЕАЛИЗАЦИЮ
        File ans = new File("sortedData");
        List<Long> inputNumbers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(data))) {
            String i = bufferedReader.readLine();
            while (i != null) {
                inputNumbers.add(Long.parseLong(i));
                i = bufferedReader.readLine();
            }
        }
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery = "INSERT INTO numbers (val) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                connection.setAutoCommit(false);
                for (int i = 0; i < inputNumbers.size(); i++) {
                    preparedStatement.setLong(1, inputNumbers.get(i)); //(2)
                    preparedStatement.addBatch(); //(3)
                    if (i % BATCH_SIZE == 0 || i == inputNumbers.size() - 1) {
                        try {
                            preparedStatement.executeBatch(); //(4)
                            connection.commit();
                        } catch (BatchUpdateException e) {
                            System.out.println(e.getMessage());
                            connection.rollback();
                        }
                    }
                }
            }
            String getQuery = "SELECT val FROM numbers ORDER BY val DESC";
            try (Statement statement = connection.createStatement();
                 PrintWriter printWriter = new PrintWriter(ans)) {
                ResultSet resultSet = statement.executeQuery(getQuery);
                while (resultSet.next()) {
                    printWriter.println(resultSet.getString("val"));
                }
            }
        }
        return ans;
    }

    public static File generate(String name, int count) throws IOException {
        Random random = new Random();
        File file = new File(name);
        try (PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < count; i++) {
                pw.println(random.nextLong());
            }
            pw.flush();
        }
        return file;
    }
}
