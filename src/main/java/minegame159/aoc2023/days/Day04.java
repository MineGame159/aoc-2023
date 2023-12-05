package minegame159.aoc2023.days;

import minegame159.aoc2023.Day;

import java.util.Arrays;
import java.util.List;

public class Day04 extends Day {
    public Day04() {
        super(4);
    }

    @Override
    public long calculateFirst(List<String> input) {
        int sum = 0;

        for (String line : input) {
            int count = parseCard(line).getWinningCount();
            if (count > 0) sum += (int) Math.pow(2, count - 1);
        }

        return sum;
    }

    @Override
    public long calculateSecond(List<String> input) {
        int[] cards = new int[input.size()];
        int total = input.size();

        Arrays.fill(cards, 1);

        for (int i = 0; i < input.size(); i++) {
            int winning = parseCard(input.get(i)).getWinningCount();

            for (int j = 0; j < winning; j++) {
                int index = i + j + 1;
                if (index < cards.length) cards[index] += cards[i];
            }

            total += winning * cards[i];
        }

        return total;
    }

    private Card parseCard(String string) {
        int colonI = string.indexOf(':');
        int id = Integer.parseInt(string.substring(string.indexOf(' ') + 1, colonI).trim());

        String[] split = string.substring(colonI + 1).split("\\|");

        return new Card(
                id,
                parseNumbers(split[0]),
                parseNumbers(split[1])
        );
    }

    private int[] parseNumbers(String string) {
        String[] split = string.split(" ");
        int length = 0;

        for (String str : split) {
            if (!str.isEmpty()) length++;
        }

        int[] numbers = new int[length];
        int i = 0;

        for (String s : split) {
            if (!s.isEmpty()) {
                numbers[i++] = Integer.parseInt(s.trim());
            }
        }

        return numbers;
    }

    record Card(int id, int[] winning, int[] guessed) {
        public int getWinningCount() {
            int count = 0;

            for (int number : guessed) {
                if (isWinning(number)) count++;
            }

            return count;
        }

        public boolean isWinning(int number) {
            for (int winning : this.winning) {
                if (number == winning) return true;
            }

            return false;
        }
    }
}
