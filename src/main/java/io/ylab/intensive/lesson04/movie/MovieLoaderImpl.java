package io.ylab.intensive.lesson04.movie;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class MovieLoaderImpl implements MovieLoader {
    private DataSource dataSource;

    private void setValue(PreparedStatement preparedStatement, int index, Object object) throws SQLException {
        if (object == null) {
            preparedStatement.setNull(index, Types.NULL);
        } else if (object instanceof Integer) {
            preparedStatement.setInt(index, (Integer) object);
        } else if (object instanceof String) {
            preparedStatement.setString(index, (String) object);
        } else if (object instanceof Boolean) {
            preparedStatement.setBoolean(index, (Boolean) object);
        }
    }

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) throws SQLException, IOException {
        // РЕАЛИЗАЦИЮ ПИШЕМ ТУТ
        List<Movie> movies;
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("year", CsvSchema.ColumnType.NUMBER)
                .addColumn("length", CsvSchema.ColumnType.NUMBER)
                .addColumn("title")
                .addColumn("subject")
                .addColumn("actors")
                .addColumn("actress")
                .addColumn("director")
                .addColumn("popularity", CsvSchema.ColumnType.NUMBER)
                .addColumn("awards", CsvSchema.ColumnType.NUMBER)
                .addColumn("image")
                .setColumnSeparator(';')
                .setUseHeader(true)
                .setSkipFirstDataRow(true)
                .build();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            MappingIterator<Movie> mi = csvMapper.readerFor(Movie.class).with(schema).readValues(reader);
            movies = mi.readAll();
        }

        String insertQuery = "insert into movie (year, length, title, subject, actors, actress, director, popularity, awards) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (Movie movie : movies) {
                setValue(preparedStatement,1, movie.getYear());
                setValue(preparedStatement,2, movie.getLength());
                setValue(preparedStatement,3, movie.getTitle());
                setValue(preparedStatement,4, movie.getSubject());
                setValue(preparedStatement,5, movie.getActors());
                setValue(preparedStatement,6, movie.getActress());
                setValue(preparedStatement,7, movie.getDirector());
                setValue(preparedStatement,8, movie.getYear());
                setValue(preparedStatement,9, movie.getAwards());
                preparedStatement.executeUpdate();
            }
        }
    }
}
