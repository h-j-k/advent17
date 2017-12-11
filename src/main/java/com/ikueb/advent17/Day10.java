package com.ikueb.advent17;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Day10 {

    private Day10() {
        // empty
    }

    static int knotAndMultiplyFirstTwo(int listSize, int[] lengths) {
        Payload payload = knot(new Payload(listSize), lengths);
        return payload.copyResult()[0] * payload.copyResult()[1];
    }

    static String hash(String value) {
        int[] lengths = IntStream.concat(
                value.chars(),
                IntStream.of(17, 31, 73, 47, 23)).toArray();
        Payload payload = IntStream.range(0, 64).boxed().reduce(
                new Payload(256),
                (r, i) -> knot(r, lengths),
                (a, b) -> {
                    throw new UnsupportedOperationException();
                });
        int[] result = payload.copyResult();
        return IntStream.range(0, 16)
                .map(i -> IntStream.range(i * 16, (i + 1) * 16)
                        .mapToObj(j -> result[j])
                        .reduce(0, (a, b) -> a ^ b))
                .mapToObj(Day10::paddedHex)
                .collect(Collectors.joining(""));
    }

    private static String paddedHex(int i) {
        return i < 16 ? "0" + Integer.toHexString(i) : Integer.toHexString(i);
    }

    private static Payload knot(Payload payload, int[] lengths) {
        int[] input = payload.copyResult();
        int start = payload.getStart();
        int skip = payload.getSkip();
        int listSize = input.length;
        IntBinaryOperator index = (a, b) -> a + b - listSize * ((a + b) / listSize);
        for (int i = 0, current;
             i < lengths.length;
             i++, start = index.applyAsInt(start, current + skip), skip++) {
            current = lengths[i];
            int t = start;
            int[] selection = IntStream.range(0, current)
                    .map(j -> index.applyAsInt(t, j))
                    .toArray();
            for (int j = 0; j < current / 2; j++) {
                int temp = input[selection[j]];
                input[selection[j]] = input[selection[current - j - 1]];
                input[selection[current - j - 1]] = temp;
            }
        }
        return new Payload(start, skip, input);
    }

    static final class Payload {
        private final int start;
        private final int skip;
        private final int[] result;

        Payload(int listSize) {
            this(0, 0, IntStream.range(0, listSize).toArray());
        }

        Payload(int start, int skip, int[] result) {
            this.start = start;
            this.skip = skip;
            this.result = Arrays.copyOf(result, result.length);
        }

        int getStart() {
            return start;
        }

        int getSkip() {
            return skip;
        }

        int[] copyResult() {
            return Arrays.stream(result).toArray();
        }
    }
}
