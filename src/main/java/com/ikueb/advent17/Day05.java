package com.ikueb.advent17;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

final class Day05 {

    private Day05() {
        // empty
    }

    static int getSteps(int... inputs) {
        return getSteps(i -> i + 1, inputs);
    }

    static int getSteps(IntUnaryOperator op, int... inputs) {
        int[] x = Arrays.copyOf(inputs, inputs.length);
        int counter = 0;
        for (int i = 0, j; i < x.length;
             j = x[i], x[i] = op.applyAsInt(x[i]), i += j) {
            counter++;
        }
        return counter;
    }
}
