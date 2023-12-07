package minegame159.aoc2023.days;

import minegame159.aoc2023.Day;

import java.util.Arrays;
import java.util.List;

public class Day07 extends Day {
    private static boolean JOKER = false;

    public Day07() {
        super(7);
    }

    @Override
    public long calculateFirst(List<String> input) {
        JOKER = false;
        return 0;
    }

    @Override
    public long calculateSecond(List<String> input) {
        JOKER = true;
        return run(input);
    }

    private long run(List<String> input) {
        Hand[] hands = parse(input);
        Arrays.sort(hands);

        int total = 0;

        for (int i = 0; i < hands.length; i++) {
            total += hands[i].bid * (i + 1);
        }

        return total;
    }

    private Hand[] parse(List<String> input) {
        Hand[] hands = new Hand[input.size()];

        for (int i = 0; i < hands.length; i++) {
            String[] splits = input.get(i).split(" ");

            char[] cards = splits[0].toCharArray();
            int bid = Integer.parseInt(splits[1]);

            hands[i] = new Hand(cards, bid, HandType.get(cards));
        }

        return hands;
    }

    record Hand(char[] cards, int bid, HandType type) implements Comparable<Hand> {
        @Override
        public int compareTo(Hand o) {
            int cmp = type.compareTo(o.type);

            if (cmp == 0) {
                for (int i = 0; i < 5; i++) {
                    cmp = Integer.compare(getCardIndex(cards[i]), getCardIndex(o.cards[i]));
                    if (cmp != 0) break;
                }
            }

            return cmp;
        }
    }

    enum HandType {
        HighCard,
        OnePair,
        TwoPair,
        ThreeOfAKind,
        FullHouse,
        FourOfAKind,
        FiveOfAKind;

        public static HandType get(char[] cards) {
            if (hasXSameGetIndex(cards, 5, -1) != -1) return FiveOfAKind;
            if (hasXSameGetIndex(cards, 4, -1) != -1) return FourOfAKind;
            if (hasXSameGetIndex(cards, 3, -1) != -1) return hasXSameGetIndex(cards, 2, -1) != -1 ? FullHouse : ThreeOfAKind;

            int i1 = hasXSameGetIndex(cards, 2, -1);
            if (i1 != -1) return hasXSameGetIndex(cards, 2, i1) != -1 ? TwoPair : OnePair;

            return HighCard;
        }

        private static int hasXSameGetIndex(char[] cards, int x, int notIncludingIndex) {
            for (int i = 0; i < 14; i++) {
                int count = 0;

                for (char card : cards) {
                    if ((notIncludingIndex == -1 || notIncludingIndex != i) && ((JOKER && card == 'J') || getCardIndex(card) == i)) count++;
                }

                if (count == x) return i;
            }

            return -1;
        }
    }

    private static int getCardIndex(char card) {
        return switch (card) {
            case '2' -> 1;
            case '3' -> 2;
            case '4' -> 3;
            case '5' -> 4;
            case '6' -> 5;
            case '7' -> 6;
            case '8' -> 7;
            case '9' -> 8;
            case 'T' -> 9;
            case 'J' -> JOKER ? 0 : 10;
            case 'Q' -> 11;
            case 'K' -> 12;
            case 'A' -> 13;

            default -> throw new IllegalArgumentException();
        };
    }
}
