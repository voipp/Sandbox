package utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class TestUtils {
    private TestUtils() {
        throw new UnsupportedOperationException();
    }

    public static File writeToTempFile(String text) throws IOException {
        File file = File.createTempFile("test-file", "tmp");
        file.deleteOnExit();


        file.setReadable(true, false);

        if (text != null) {
            PrintWriter writer = new PrintWriter(file);
            writer.print(text);
            writer.flush();
            writer.close();
        }

        return file;
    }
}
