import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.TestUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * GeneratorRunnerTest.
 *
 * @author Aleksey_Kuznetsov
 */
public class GeneratorRunnerTest {
    private String resultFileName = "result0";

    @Before
    public void setUp() {
        File file = new File(resultFileName);

        if (file.exists())
            file.delete();
    }

    @Test
    public void testAllCorrect() throws Exception {
        Long opCount = 10L;

        File file = TestUtils.writeToTempFile("The first line.\nThe second line.\nThe third line.");

        TestDataGeneratorRunner.main(new String[]{file.getPath(), String.valueOf(opCount), resultFileName});

        File res = new File(resultFileName);

        Assert.assertTrue(res.exists() && res.canRead());

        assertResultFileCorrect(res);
    }

    @Test
    public void testAllCorrect2() throws Exception {
        Long opCount = 0L;

        File file = TestUtils.writeToTempFile("The first line.\nThe second line.");

        TestDataGeneratorRunner.main(new String[]{file.getPath(), String.valueOf(opCount), resultFileName});

        File res = new File(resultFileName);

        Assert.assertTrue(res.exists() && res.canRead());

        Assert.assertFalse(new Scanner(res).hasNextLine());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateArgs() throws Exception {
        Long opCount = 0L;

        TestDataGeneratorRunner.main(new String[]{String.valueOf(opCount), resultFileName});
    }

    private void assertResultFileCorrect(File res) throws FileNotFoundException {
        Scanner s = new Scanner(res);

        while (s.hasNextLine()) {
            String line0 = s.nextLine();

            String[] line1 = line0.split(",");

            Assert.assertEquals(4, line1.length);

            LocalDateTime.parse(line1[0]);// assert arg is parsable.

            Long.parseLong(line1[2]);// assert arg is parsable

            Float.parseFloat(line1[3]);
        }
    }
}
