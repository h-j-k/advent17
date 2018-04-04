package com.ikueb.advent17;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

final class Day01 {

    private Day01() {
        // empty
    }

    static int compute(String value, ToIntFunction<int[]> function) {
        return function.applyAsInt(value.chars()
                .map(c -> Character.digit((char) c, 10)).toArray());
    }

    static int sum(int... values) {
        return sum(values.length, i -> i + 1 == values.length ? 0 : i + 1,
                (a, b) -> a == b ? a : 0, values);
    }

    static int jumpSum(int... values) {
        var half = values.length / 2;
        return sum(half, i -> i + half, (a, b) -> a == b ? 2 * a : 0, values);
    }

    private static int sum(int end, IntUnaryOperator pair,
                           IntBinaryOperator op, int... values) {
        return IntStream.range(0, end)
                .map(i -> op.applyAsInt(values[i], values[pair.applyAsInt(i)])).sum();
    }
}
