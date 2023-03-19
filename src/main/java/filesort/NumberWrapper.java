package filesort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class NumberWrapper implements Comparable<NumberWrapper> {
    private long number;
    private BufferedReader reader;
    private InputStream inputStream;

    public NumberWrapper(long number, BufferedReader reader, InputStream inputStream) {
        this.number = number;
        this.reader = reader;
        this.inputStream = inputStream;
    }

    public long getNumber() {
        return number;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void close() throws IOException {
        reader.close();
        inputStream.close();
    }

    @Override
    public int compareTo(NumberWrapper o) {
        return Long.compare(number, o.number);
    }
}