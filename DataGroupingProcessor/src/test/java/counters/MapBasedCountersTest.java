package counters;

import org.junit.Assert;
import org.junit.Test;
import utils.TestReader;

import java.io.IOException;
import java.io.StringWriter;

public class MapBasedCountersTest {
    @Test
    public void testMapBasedDateCounter() throws IOException {
        MapBasedDateCounter counter = new MapBasedDateCounter();
        StringWriter writer = new StringWriter();
        TestReader reader = new TestReader(
                "2018-11-17T18:44:45,The second line.,1,1\n" +
                        "2018-11-17T01:19:40,The second line.,2,2\n" +
                        "2018-10-28T03:08:29,The first line.,3,3\n" +
                        "2018-10-28T09:13:48,The first line.,4,4\n" +
                        "2018-10-28T15:33:49,The first line.,5,5\n" +
                        "2018-10-28T17:17:26,The third line.,6,6\n" +
                        "2018-11-17T08:14:30,The first line.,7,7\n" +
                        "2018-10-28T10:57:22,The third line.,8,8\n" +
                        "2018-10-28T18:01:57,The first line.,9,9\n" +
                        "2018-11-17T07:25:14,The second line.,10,10");

        counter.count(writer, reader);

        Assert.assertEquals("2018-10-28,35.0\n" +
                "2018-11-17,20.0", writer.toString());
    }

    @Test
    public void testCorruptedInputData() throws IOException {
        MapBasedDateCounter counter = new MapBasedDateCounter();
        StringWriter writer = new StringWriter();
        TestReader reader = new TestReader(
                "2018-11-17T18:44:45,The second");

        counter.count(writer, reader);

        Assert.assertTrue(writer.toString().isEmpty());
    }

    @Test
    public void testEmptyInputData() throws IOException {
        MapBasedDateCounter counter = new MapBasedDateCounter();
        MapBasedOfficeCounter counter1 = new MapBasedOfficeCounter();
        StringWriter writer = new StringWriter();
        TestReader reader = new TestReader();

        counter.count(writer, reader);

        Assert.assertTrue(writer.toString().isEmpty());

        counter1.count(writer, reader);

        Assert.assertTrue(writer.toString().isEmpty());
    }

    @Test
    public void testMapBasedOfficeCounter() throws IOException {
        MapBasedOfficeCounter counter = new MapBasedOfficeCounter();
        StringWriter writer = new StringWriter();
        TestReader reader = new TestReader(
                "2018-11-17T18:44:45,The second line.,1,1\n" +
                        "2018-02-27T01:19:40,The second line.,2,2\n" +
                        "2018-10-28T03:08:29,The first line.,3,3\n" +
                        "2018-04-21T09:13:48,The first line.,4,4\n" +
                        "2018-12-06T15:33:49,The first line.,5,5\n" +
                        "2018-09-10T17:17:26,The third line.,6,6\n" +
                        "2018-09-08T08:14:30,The first line.,7,7\n" +
                        "2018-05-22T10:57:22,The third line.,8,8\n" +
                        "2018-01-21T18:01:57,The first line.,9,9\n" +
                        "2018-01-27T07:25:14,The second line.,10,10");
        counter.count(writer, reader);

        Assert.assertEquals("The first line.,28.0\n" +
                        "The third line.,14.0\n" +
                "The second line.,13.0"
                , writer.toString());
    }
}