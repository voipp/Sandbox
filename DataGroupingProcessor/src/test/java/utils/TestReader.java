package utils;

import filereaders.ResourceReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public class TestReader implements ResourceReader {
    private String payload;

    public TestReader(String payload) {
        this.payload = payload;
    }

    public TestReader() {
    }

    @Override
    public String read() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<String> stream() {
        if (payload != null)
            return Arrays.stream(payload.split("\n"));
        else return Stream.empty();
    }
}
