import counters.MapBasedDateCounter;
import counters.MapBasedOfficeCounter;
import filereaders.CSVFileReader;

import java.io.FileWriter;
import java.io.IOException;

public class DataGroupingProcessorRunner {
    public static void main(String[] args) throws IOException {
        validateArguments(args);

        MapBasedDateCounter counter0 = new MapBasedDateCounter();

        counter0.count(new FileWriter(args[1]), new CSVFileReader(args[0]));

        MapBasedOfficeCounter counter1 = new MapBasedOfficeCounter();

        counter1.count(new FileWriter(args[2]), new CSVFileReader(args[0]));
    }

    private static void validateArguments(String[] args) {
        if (args.length != 3)
            throw new IllegalArgumentException("Wrong arguments received! " + correctUsageMenu());
    }

    private static String correctUsageMenu() {
        return "Correct command line arguments: <operations> <statByDate> <statByOffices>" +
                "\n\t<operations> Operations data.\n\t<statByDate> Statistics by date.\n" +
                "\t<statByOffices> Statistics by offices.";
    }
}
