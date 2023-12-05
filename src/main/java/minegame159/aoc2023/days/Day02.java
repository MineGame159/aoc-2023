package minegame159.aoc2023.days;

import minegame159.aoc2023.Day;

import java.util.List;

public class Day02 extends Day {
    private static final Set SET = new Set(13, 12, 14);

    public Day02() {
        super(2);
    }

    @Override
    public long calculateFirst(List<String> input) {
        int sum = 0;

        for (String line : input) {
            Game game = parseGame(line);
            if (game.isPossible(SET)) sum += game.id;
        }

        return sum;
    }

    @Override
    public long calculateSecond(List<String> input) {
        int sum = 0;

        for (String line : input) {
            Game game = parseGame(line);
            sum += game.getBiggest().power();
        }

        return sum;
    }

    private Game parseGame(String string) {
        String[] splits = string.split(":");

        // ID
        int id = Integer.parseInt(splits[0].substring(5));

        // Sets
        String[] setSplits = splits[1].split(";");
        Set[] sets = new Set[setSplits.length];

        for (int i = 0; i < setSplits.length; i++) {
            sets[i] = parseSet(setSplits[i]);
        }

        return new Game(id, sets);
    }

    private Set parseSet(String string) {
        int red = 0;
        int green = 0;
        int blue = 0;

        for (String split : string.split(",")) {
            String[] splits = split.trim().split(" ");

            int count = Integer.parseInt(splits[0]);
            String color = splits[1];

            switch (color) {
                case "red" -> red = count;
                case "green" -> green = count;
                case "blue" -> blue = count;
            }
        }

        return new Set(red, green, blue);
    }

    record Game(int id, Set[] sets) {
        public boolean isPossible(Set set) {
            for (Set ownSet : sets) {
                if (ownSet.isGreater(set)) {
                    return false;
                }
            }

            return true;
        }

        public Set getBiggest() {
            int red = 0;
            int green = 0;
            int blue = 0;

            for (Set set : sets) {
                red = Math.max(red, set.red);
                green = Math.max(green, set.green);
                blue = Math.max(blue, set.blue);
            }

            return new Set(red, green, blue);
        }
    }

    record Set(int red, int green, int blue) {
        public boolean isGreater(Set set) {
            return red > set.red || green > set.green || blue > set.blue;
        }

        public int power() {
            return red * green * blue;
        }
    }
}
