package com.ikueb.advent17;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class Day17 {

    private Day17() {
        // empty
    }

    static int getAfter2017For2017(int steps) {
        var values = new int[]{0};
        for (int i = 1, index = 1; ;
             i++, index = (index + steps) % i + 1) {
            values = Stream.of(Arrays.stream(values, 0, index),
                    IntStream.of(i), Arrays.stream(values, index, i))
                    .flatMapToInt(Function.identity())
                    .toArray();
            if (i == 2017) {
                return values[index + 1];
            }
        }
    }

    static int getAfter0For50Million(int steps) {
        var result = 0;
        for (int i = 1, index = 1; i <= 50_000_000;
             i++, index = (index + steps) % i + 1) {
            if (index == 1) {
                result = i;
            }
        }
        return result;
    }
}
