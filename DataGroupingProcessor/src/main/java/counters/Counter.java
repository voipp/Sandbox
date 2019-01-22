package counters;

import filereaders.ResourceReader;

import java.io.IOException;
import java.io.Writer;

/**
 * Counter for test data.
 */
public interface Counter {
    void count(Writer outputSource, ResourceReader reader) throws IOException;
}
