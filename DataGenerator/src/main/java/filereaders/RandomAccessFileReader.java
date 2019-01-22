package filereaders;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Reads random line from text file.
 *
 * @author Aleksey_Kuznetsov
 */
public class RandomAccessFileReader implements ResourceReader {
    private final String pathName;

    public RandomAccessFileReader(String pathName) {
        this.pathName = pathName;
    }

    public String read() throws IOException {
        File file = new File(pathName);

        RandomAccessFile f = new RandomAccessFile(file, "r");

        long pointer = RandomUtils.nextLong(0, file.length());

        f.seek(pointer);

        f.readLine();

        String line;

        int b = f.read();

        while (true) {
            if (b == '\n' || b == '\t' || b == '\r')
                b = f.read();
            else if (b < 0) {
                f.seek(0);

                break;
            } else {
                f.seek(f.getFilePointer() - 1);

                break;
            }
        }

        line = f.readLine();

        return line == null ? StringUtils.EMPTY : line;
    }

    @Override
    public Stream stream() throws IOException {
        return Files.lines(Paths.get(pathName));
    }
}
