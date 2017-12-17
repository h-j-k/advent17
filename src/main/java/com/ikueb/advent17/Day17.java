package com.ikueb.advent17;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class Day17 {

    private Day17() {
        // empty
    }

    static int getNextAfter2017WithSteps(int steps) {
        int[] values = {0};
        int index = 0;
        for (int i = 1; i <= 2017; i++) {
            index += steps + 1;
            while (index > values.length) {
                index -= values.length;
            }
            values = Stream.of(Arrays.stream(values, 0, index),
                    IntStream.of(i), Arrays.stream(values, index, values.length))
                    .flatMapToInt(Function.identity())
                    .toArray();
        }
        return values[index + 1];
    }
}
