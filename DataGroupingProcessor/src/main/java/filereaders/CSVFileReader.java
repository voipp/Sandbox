package filereaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * File reader that reads csv and parse it into particles.
 */
public class CSVFileReader implements ResourceReader {
    private final String filePath;
    private final Scanner scanner;

    public CSVFileReader(String filePath) throws FileNotFoundException {
        scanner = new Scanner(new File(filePath));
        this.filePath = filePath;
    }

    public String read() {
        if (scanner.hasNextLine())
            return scanner.next();

        return null;
    }

    public Stream<String> stream() throws IOException {
        return Files.lines(Paths.get(filePath));
    }
}
