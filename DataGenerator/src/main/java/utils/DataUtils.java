package utils;

import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class DataUtils {
    private DataUtils(){
        throw new UnsupportedOperationException();
    }

    /**
     * @param year year.
     * @return LocalDateTime with year inserted and days, hours etc. set to zero.
     */
    public static LocalDateTime getFirstDateOfTheYear(int year){
        return LocalDateTime.of(year, 1, 1, 0, 0, 0, 0);
    }

    /**
     * Get random date between bounds.
     *
     * @param dateFrom   left date bound.
     * @param dateUntil  right date bound.
     * @return           random date.
     */
    public static LocalDateTime nextRandomDate(LocalDateTime dateFrom, LocalDateTime dateUntil){
        try {
            long delta = dateFrom.until(dateUntil, ChronoUnit.MILLIS);

            long randomTime = RandomUtils.nextLong(0, delta);

            dateFrom.plus(randomTime, ChronoUnit.MILLIS);

            return dateFrom.plus(randomTime, ChronoUnit.MILLIS).withNano(0);

        } catch (Exception ignore){
            throw new IllegalArgumentException("Invalid dates received.\nFirst date: " + dateFrom
                    + ".\nSecond date: " + dateUntil);
        }
    }
}
