package utils;

import java.util.function.Predicate;

/**
 * Validates that string input is 4 part string.
 */
public class InputValidator implements Predicate<String[]> {
    @Override
    public boolean test(String[] s) {
        return s.length == 4;
    }
}
