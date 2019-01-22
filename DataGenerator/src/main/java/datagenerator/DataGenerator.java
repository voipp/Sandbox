package datagenerator;

import filereaders.ResourceReader;

import java.io.Writer;

/**
 * Data generator.
 */
public interface DataGenerator {
    void generate(ResourceReader reader, Writer writer, String[] args) throws Exception;
}
