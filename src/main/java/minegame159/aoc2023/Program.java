package minegame159.aoc2023;

import minegame159.aoc2023.days.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Program {
    private static final Day[] DAYS = {
            new Day01(),
            new Day02(),
            new Day03(),
            new Day04(),
            new Day05(),
            new Day06(),
            new Day07(),
    };

    public static void main(String[] args) {
        Day day = getDay(args);
        List<String> input = readInput(day);

        run(day, input, true);
        run(day, input, false);
    }

    private static Day getDay(String[] args) {
        int number = DAYS.length;

        if (args.length > 0) {
            try {
                number = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException ignored) {}
        }

        return DAYS[number - 1];
    }

    private static List<String> readInput(Day day) {
        try (InputStream in = Program.class.getResourceAsStream(String.format("/input/%d.txt", day.number))) {
            Objects.requireNonNull(in);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                List<String> input = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null) {
                    input.add(line);
                }

                return input;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void run(Day day, List<String> input, boolean first) {
        long start = System.nanoTime();
        long result = first ? day.calculateFirst(input) : day.calculateSecond(input);
        long duration = System.nanoTime() - start;

        System.out.println();
        System.out.printf(" --- Day %d - Part %d --- \n", day.number, first ? 1 : 2);
        System.out.printf("Result: %d\n", result);
        System.out.printf("Time: %f ms\n", duration / 1000000.0);
    }
}
