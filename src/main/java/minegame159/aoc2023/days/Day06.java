package minegame159.aoc2023.days;

import minegame159.aoc2023.Day;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Day06 extends Day {
    public Day06() {
        super(6);
    }

    @Override
    public long calculateFirst(List<String> input) {
        Race[] races = parseRaces(input);
        int result = 1;

        for (Race race : races) {
            result *= race.getBeatCount();
        }

        return result;
    }

    @Override
    public long calculateSecond(List<String> input) {
        return parseRace(input).getBeatCount();
    }

    private Race[] parseRaces(List<String> input) {
        String[] timeSplits = Arrays.stream(input.get(0).substring(input.get(0).indexOf(':') + 1).split(" ")).filter(Predicate.not(String::isEmpty)).toArray(String[]::new);
        String[] recordSplits = Arrays.stream(input.get(1).substring(input.get(1).indexOf(':') + 1).split(" ")).filter(Predicate.not(String::isEmpty)).toArray(String[]::new);

        Race[] races = new Race[timeSplits.length];

        for (int i = 0; i < timeSplits.length; i++) {
            races[i] = new Race(
                    Long.parseLong(timeSplits[i]),
                    Long.parseLong(recordSplits[i])
            );
        }

        return races;
    }

    private Race parseRace(List<String> input) {
        String time = input.get(0).substring(input.get(0).indexOf(':') + 1).chars().filter(Character::isDigit).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        String record = input.get(1).substring(input.get(1).indexOf(':') + 1).chars().filter(Character::isDigit).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        return new Race(
                Long.parseLong(time),
                Long.parseLong(record)
        );
    }

    record Race(long time, long record) {
        public int getBeatCount() {
            int count = 0;

            for (long i = 0; i < time; i++) {
                if (canBeat(i)) count++;
            }

            return count;
        }

        public boolean canBeat(long holdFor) {
            long distance = (time - holdFor) * holdFor;
            return distance > record;
        }
    }
}
