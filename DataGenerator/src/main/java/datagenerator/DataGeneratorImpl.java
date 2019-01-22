package datagenerator;

import filereaders.ResourceReader;
import org.apache.commons.lang3.RandomUtils;
import utils.DataUtils;

import java.io.Writer;
import java.time.LocalDateTime;


/**
 * Data generator that extracts random date from previous year,
 * get random data from the file and adds some random date.
 */
public class DataGeneratorImpl implements DataGenerator {
    private void validateArgs(String[] args){
        try {
            Long.parseLong(args[1]);
        } catch (Exception ignore) {
            throw new IllegalArgumentException("Wrong arguments received! " +
                    "Expected second arg to be number.");
        }
    }

    public void generate(ResourceReader reader, Writer writer, String[] args) throws Exception {
        validateArgs(args);

        LocalDateTime curYearFirstDate = DataUtils.getFirstDateOfTheYear(LocalDateTime.now().getYear());
        LocalDateTime prevYearFirstDate = DataUtils.getFirstDateOfTheYear(LocalDateTime.now().getYear() - 1);

        try {
            for (int i = 1; i <= Long.valueOf(args[1]); i++) {
                StringBuilder resultBuilder = new StringBuilder();

                StringBuilder result = resultBuilder
                        .append(DataUtils.nextRandomDate(prevYearFirstDate, curYearFirstDate).toString()).append(",")
                        .append(reader.read()).append(",")
                        .append(i).append(",")
                        .append(getRandomSum())
                        .append("\n");

                writer.write(result.toString());
                writer.flush();
            }

        } finally {
            writer.close();
        }
    }

    private static String getRandomSum() {
        return String.format("%.2f", RandomUtils.nextFloat(10_000, 100_000)).replace(",", ".");
    }
}
