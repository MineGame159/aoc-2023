package minegame159.aoc2023;

import java.util.List;

public abstract class Day {
    public final int number;

    public Day(int number) {
        this.number = number;
    }

    public abstract int calculateFirst(List<String> input);

    public abstract int calculateSecond(List<String> input);
}
