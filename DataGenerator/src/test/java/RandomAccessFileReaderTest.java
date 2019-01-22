import filereaders.RandomAccessFileReader;
import org.junit.Assert;
import org.junit.Test;
import utils.TestUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * RandomAccessFileReaderTest.
 *
 * @author Aleksey_Kuznetsov
 */
public class RandomAccessFileReaderTest {
    @Test
    public void readFromFile() throws IOException {
        File file = TestUtils.writeToTempFile("The first line.\nThe second line.");

        RandomAccessFileReader reader = new RandomAccessFileReader(file.getPath());

        String res = reader.read();

        Assert.assertNotNull(res);
    }

    @Test
    public void readFromFile2() throws IOException {
        File file = TestUtils.writeToTempFile("The first line");

        RandomAccessFileReader reader = new RandomAccessFileReader(file.getPath());

        String res = reader.read();
        Assert.assertEquals("The first line", res);
    }

    @Test
    public void readFromFile3() throws IOException {
        File file = TestUtils.writeToTempFile("The first line.");

        RandomAccessFileReader reader = new RandomAccessFileReader(file.getPath());

        String res = reader.read();
        Assert.assertEquals("The first line.", res);
    }

    @Test
    public void readFromFile4() throws IOException {
        File file = TestUtils.writeToTempFile("\n");

        RandomAccessFileReader reader = new RandomAccessFileReader(file.getPath());

        String res = reader.read();
        Assert.assertTrue(res.isEmpty());
    }

    @Test
    public void readFromFile5() throws IOException {
        File file = TestUtils.writeToTempFile(null);

        RandomAccessFileReader reader = new RandomAccessFileReader(file.getPath());

        String res = reader.read();
        Assert.assertTrue(res.isEmpty());
    }
}