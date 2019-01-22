package counters;


import filereaders.ResourceReader;
import utils.InputValidator;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

/**
 * Counter is backed by map.
 */
public class MapBasedDateCounter implements Counter {
    private Function<String[], LocalDate> keyFunc = line -> LocalDateTime.parse(line[0]).toLocalDate();
    private Function<String[], Float> valFunc = line -> Float.parseFloat(line[3]);
    private BinaryOperator<Float> sumFunc = (aFloat, aFloat2) -> aFloat + aFloat2;

    @SuppressWarnings("unchecked")
    public void count(Writer outputSource, ResourceReader reader) throws IOException {
        Map<LocalDate, Float> res = (Map<LocalDate, Float>) reader.stream()
                .map(line -> ((String) line).split(","))
                .filter(new InputValidator())
                .collect(toMap(keyFunc, valFunc, sumFunc, TreeMap::new));

        try {
            boolean firstLine = true;

            for (Map.Entry<LocalDate, Float> localDateFloatEntry : res.entrySet()) {
                outputSource.write((firstLine ? "" : "\n") +
                        localDateFloatEntry.getKey().toString() + "," +
                        localDateFloatEntry.getValue());

                outputSource.flush();

                firstLine = false;
            }

        } finally {
            outputSource.close();
        }
    }
}
