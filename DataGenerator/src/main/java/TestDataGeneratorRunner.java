import datagenerator.DataGenerator;
import datagenerator.DataGeneratorImpl;
import filereaders.RandomAccessFileReader;

import java.io.FileWriter;


/**
 * Test data generator.
 */
public class TestDataGeneratorRunner {
    public static void main(String[] args) throws Exception {
        validateArguments(args);

        DataGenerator dataGenerator = new DataGeneratorImpl();

        try {
            dataGenerator.generate(new RandomAccessFileReader(args[0]),
                    new FileWriter(args[2]),
                    args);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * @param e             exception occurred.
     */
    private static void handleException(Exception e) {
        System.err.println("Some errors occurred during test data generation.");
    }

    private static void validateArguments(String[] args) {
        if (args.length != 3)
            throw new IllegalArgumentException("Wrong arguments received! " + correctUsageMenu());
    }

    private static String correctUsageMenu() {
        return "Correct command line arguments: <officesList> <opCount> <resultFIle>" +
                "\n\t<officesList> Offices list file\n\t<opCount> Operations count\n\t<resultFIle> result file";
    }
}
