package counters;

import filereaders.ResourceReader;
import utils.InputValidator;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Counter is backed by simple map.
 */
public class MapBasedOfficeCounter implements Counter {
    private Function<String[], String> keyFunc = line -> line[1];
    private Function<String[], Float> valFunc = line -> Float.parseFloat(line[3]);
    private BinaryOperator<Float> sumFunc = (aFloat, aFloat2) -> aFloat + aFloat2;

    @SuppressWarnings("unchecked")
    public void count(Writer outputSource, ResourceReader reader) throws IOException {
        Object[] res = ((Map<String, Float>) reader.stream()
                .map(line -> ((String) line).split(","))
                .filter(new InputValidator())
                .collect(toMap(keyFunc, valFunc, sumFunc)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Float>comparingByValue().reversed())
                .toArray();
        try {
            boolean firstLine = true;

            for (Object str : res) {
                Map.Entry entry = (Map.Entry) str;

                outputSource.write((firstLine ? "" : "\n") + entry.getKey() + "," + entry.getValue());

                outputSource.flush();

                firstLine = false;
            }

        } finally {
            outputSource.close();
        }
    }
}
