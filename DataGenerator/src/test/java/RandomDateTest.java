import org.junit.Assert;
import org.junit.Test;
import utils.DataUtils;

import java.time.LocalDateTime;

/**
 * RandomDateTest.
 *
 * @author Aleksey_Kuznetsov
 */
public class RandomDateTest {

    @Test
    public void testAfterEpochYear() {
        LocalDateTime curYearFirstDate = DataUtils.getFirstDateOfTheYear(2018);
        LocalDateTime prevYearFirstDate = DataUtils.getFirstDateOfTheYear(2017);

        LocalDateTime randomDate = DataUtils.nextRandomDate(prevYearFirstDate, curYearFirstDate);

        Assert.assertTrue(randomDate.getYear() < curYearFirstDate.getYear());
        Assert.assertTrue(randomDate.getYear() >= prevYearFirstDate.getYear());
    }

    @Test
    public void testEpochYear() {
        LocalDateTime now = LocalDateTime.now().withYear(1970);

        LocalDateTime curYearFirstDate = DataUtils.getFirstDateOfTheYear(1970);
        LocalDateTime prevYearFirstDate = DataUtils.getFirstDateOfTheYear(1969);

        LocalDateTime randomDate = DataUtils.nextRandomDate(prevYearFirstDate, curYearFirstDate);

        Assert.assertTrue(randomDate.getYear() < 1970);
        Assert.assertTrue(randomDate.getYear() >= 1968);
    }

    @Test
    public void testBeforeEpochYear() {
        LocalDateTime curYearFirstDate = DataUtils.getFirstDateOfTheYear(1930);
        LocalDateTime prevYearFirstDate = DataUtils.getFirstDateOfTheYear(1929);

        LocalDateTime randomDate = DataUtils.nextRandomDate(prevYearFirstDate, curYearFirstDate);

        Assert.assertTrue(randomDate.getYear() < 1930);
        Assert.assertTrue(randomDate.getYear() >= 1928);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectYears() {
        DataUtils.nextRandomDate(null, LocalDateTime.now());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectDates() {
        DataUtils.nextRandomDate(LocalDateTime.now().plusDays(5), LocalDateTime.now());
    }
}
