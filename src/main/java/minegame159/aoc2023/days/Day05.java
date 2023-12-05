package minegame159.aoc2023.days;

import minegame159.aoc2023.Day;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day05 extends Day {
    public Day05() {
        super(5);
    }

    @Override
    public long calculateFirst(List<String> input) {
        long[] seeds = parseSeeds(input.get(0));
        Almanac almanac = parseAlmanac(input);

        long lowest = Long.MAX_VALUE;

        for (long seed : seeds) {
            lowest = Math.min(lowest, almanac.convert(seed));
        }

        return lowest;
    }

    @Override
    public long calculateSecond(List<String> input) {
        long[] seedPairs = parseSeeds(input.get(0));
        Almanac almanac = parseAlmanac(input);

        long lowest = Long.MAX_VALUE;

        for (int i = 0; i < seedPairs.length; i += 2) {
            long start = seedPairs[i];
            long end = start + seedPairs[i + 1];

            for (long seed = start; seed < end; seed++) {
                lowest = Math.min(lowest, almanac.convert(seed));
            }
        }

        return lowest;
    }

    private long[] parseSeeds(String string) {
        String[] splits = string.substring(string.indexOf(' ') + 1).split(" ");
        long[] seeds = new long[splits.length];

        for (int i = 0; i < splits.length; i++) {
            seeds[i] = Long.parseLong(splits[i]);
        }

        return seeds;
    }

    private Almanac parseAlmanac(List<String> input) {
        List<Map> maps = new ArrayList<>();
        List<Range> ranges = null;

        for (String string : input) {
            if (string.endsWith("map:")) {
                if (ranges != null) maps.add(newMap(ranges));
                ranges = new ArrayList<>();
            }
            else if (ranges != null && !string.isEmpty()) {
                String[] splits = string.split(" ");

                long dstStart = Long.parseLong(splits[0]);
                long srcStart = Long.parseLong(splits[1]);
                long length = Long.parseLong(splits[2]);

                ranges.add(new Range(dstStart, srcStart, srcStart + length));
            }
        }

        if (ranges != null) maps.add(newMap(ranges));
        return new Almanac(maps.toArray(new Map[0]));
    }

    private Map newMap(List<Range> ranges) {
        ranges.sort(Comparator.comparingLong(Range::srcStart));

        int middleI = ranges.size() / 2;
        long middleNumber = ranges.get(middleI).srcStart;

        return new Map(ranges.toArray(new Range[0]), middleNumber, middleI);
    }

    record Almanac(Map[] maps) {
        public long convert(long number) {
            for (Map map : maps) {
                number = map.convert(number);
            }

            return number;
        }
    }

    record Map(Range[] ranges, long middleNumber, int middleI) {
        public long convert(long number) {
            int length = ranges.length;

            for (int i = number < middleNumber ? 0 : middleI; i < length; i++) {
                Range range = ranges[i];
                if (range.contains(number)) return range.convert(number);
            }

            return number;
        }
    }

    record Range(long dstStart, long srcStart, long srcEnd) {
        public boolean contains(long number) {
            return number >= srcStart && number < srcEnd;
        }

        public long convert(long number) {
            return number - srcStart + dstStart;
        }
    }
}
