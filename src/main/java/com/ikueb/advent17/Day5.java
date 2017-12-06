package com.ikueb.advent17;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

class Day5 {

    private Day5() {
        // empty
    }

    static int getSteps(int... inputs) {
        return getSteps(i -> i + 1, inputs);
    }

    static int getSteps(IntUnaryOperator op, int... inputs) {
        int[] instructions = Arrays.copyOf(inputs, inputs.length);
        int counter = 0;
        for (int i = 0, j; i < instructions.length; i += j, counter++) {
            j = instructions[i];
            instructions[i] = op.applyAsInt(instructions[i]);
        }
        return counter;
    }
}
