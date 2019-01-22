package filereaders;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Reads data from some resource. It can be file, or database, or net etc.
 */
public interface ResourceReader {
    String read() throws IOException;

    Stream stream() throws IOException;
}
