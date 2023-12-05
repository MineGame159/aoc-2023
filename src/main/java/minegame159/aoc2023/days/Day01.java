package minegame159.aoc2023.days;

import minegame159.aoc2023.Day;

import java.util.List;

public class Day01 extends Day {
    private static final String[] DIGITS = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };

    public Day01() {
        super(1);
    }

    @Override
    public long calculateFirst(List<String> input) {
        int sum = 0;

        for (String line : input) {
            sum += Integer.parseInt(getFirst(line, false) + "" + getLast(line, false));
        }

        return sum;
    }

    @Override
    public long calculateSecond(List<String> input) {
        int sum = 0;

        for (String line : input) {
            sum += Integer.parseInt(getFirst(line, true) + "" + getLast(line, true));
        }

        return sum;
    }

    private int getFirst(String string, boolean allowWords) {
        for (int i = 0; i < string.length(); i++) {
            int digit = getDigit(string, i, allowWords);
            if (digit != -1) return digit;
        }

        throw new IllegalArgumentException();
    }

    private int getLast(String string, boolean allowWords) {
        for (int i = string.length() - 1; i >= 0; i--) {
            int digit = getDigit(string, i, allowWords);
            if (digit != -1) return digit;
        }

        throw new IllegalArgumentException();
    }

    private int getDigit(String string, int offset, boolean allowWords) {
        char ch = string.charAt(offset);

        if (Character.isDigit(ch)) {
            return ch - '0';
        }
        else if (allowWords) {
            for (int i = 0; i < DIGITS.length; i++) {
                if (string.startsWith(DIGITS[i], offset)) {
                    return i + 1;
                }
            }
        }

        return -1;
    }
}
