package minegame159.aoc2023.days;

import minegame159.aoc2023.Day;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends Day {
    public Day03() {
        super(3);
    }

    @Override
    public int calculateFirst(List<String> input) {
        Schematic schematic = parse(input);
        int sum = 0;

        for (Number number : schematic.numbers) {
            if (schematic.isPart(number)) sum += number.value;
        }

        return sum;
    }

    @Override
    public int calculateSecond(List<String> input) {
        Schematic schematic = parse(input);
        int sum = 0;

        for (int x = 0; x < schematic.width(); x++) {
            for (int y = 0; y < schematic.height(); y++) {
                sum += schematic.getGearRatio(x, y);
            }
        }

        return sum;
    }

    private Schematic parse(List<String> input) {
        int width = input.get(0).length();
        int height = input.size();

        List<Number> numbers = new ArrayList<>();
        Cell[][] cells = new Cell[width][height];

        int numberStart;

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);

            numberStart = -1;

            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);

                if (Character.isDigit(ch)) {
                    if (numberStart == -1) numberStart = x;
                }
                else {
                    if (numberStart != -1) {
                        numbers.add(new Number(numberStart, y, Integer.parseInt(line, numberStart, x, 10)));
                        cells[y][x] = new Cell(false, numbers.size() - 1);

                        numberStart = -1;
                    }

                    if (ch != '.') {
                        cells[y][x] = new Cell(true, ch);
                    }
                }
            }

            int x = line.length();

            if (numberStart != -1) {
                numbers.add(new Number(numberStart, y, Integer.parseInt(line, numberStart, x, 10)));
                cells[y][x - 1] = new Cell(false, numbers.size() - 1);
            }
        }

        return new Schematic(numbers, cells);
    }

    record Schematic(List<Number> numbers, Cell[][] cells) {
        public boolean isPart(Number number) {
            for (int x = Math.max(0, number.x - 1); x < Math.min(width(), number.x + number.length() + 1); x++) {
                for (int y = Math.max(0, number.y - 1); y < Math.min(height(), number.y + 2); y++) {
                    if (x >= number.x && x < number.x + number.length() && y == number.y) continue;
                    if (isSymbol(x, y)) return true;
                }
            }

            return false;
        }

        public int getGearRatio(int x, int y) {
            if (!isGearSymbol(x, y)) return 0;

            Number part1 = null;
            Number part2 = null;

            for (int x2 = Math.max(0, x - 1); x2 < Math.min(width(), x + 2); x2++) {
                for (int y2 = Math.max(0, y - 1); y2 < Math.min(height(), y + 2); y2++) {
                    Number number = getNumber(x2, y2);

                    if (number != null) {
                        if (part1 == number || part2 == number) continue;
                        if (part1 != null && part2 != null) return 0;

                        if (part1 == null) part1 = number;
                        else part2 = number;
                    }
                }
            }

            if (part1 == null || part2 == null) {
                return 0;
            }

            return part1.value * part2.value;
        }

        public boolean isSymbol(int x, int y) {
            Cell cell = cells[y][x];
            return cell != null && cell.symbol;
        }

        public boolean isGearSymbol(int x, int y) {
            Cell cell = cells[y][x];
            return cell != null && cell.symbol && cell.value == '*';
        }

        public Number getNumber(int x, int y) {
            for (Number number : numbers) {
                if (number.isInside(x, y)) return number;
            }

            return null;
        }

        public int width() {
            return cells[0].length;
        }

        public int height() {
            return cells.length;
        }
    }

    record Number(int x, int y, int value) {
        public boolean isInside(int x, int y) {
            return x >= this.x && x < this.x + this.length() && y == this.y;
        }

        public int length() {
            if (value < 10) return 1;
            if (value < 100) return 2;
            if (value < 1000) return 3;
            if (value < 10000) return 4;

            throw new IllegalStateException();
        }
    }

    record Cell(boolean symbol, int value) {}
}
