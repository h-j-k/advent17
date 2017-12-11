package com.ikueb.advent17;

import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

final class Day10 {

    private Day10() {
        // empty
    }

    static int knotAndMultiplyFirstTwo(int listSize, int[] lengths) {
        int[] input = IntStream.range(0, listSize).toArray();
        IntBinaryOperator index = (a, b) -> a + b - listSize * ((a + b) / listSize);
        for (int i = 0, skip = 0, current, start = 0;
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
        return input[0] * input[1];
    }
}
