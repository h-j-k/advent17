package com.ikueb.advent17;

import java.util.function.IntUnaryOperator;

class Day5 {

    private Day5() {
        // empty
    }

    static int getSteps(int... instructions) {
        return getSteps(i -> i + 1, instructions);
    }

    static int getSteps(IntUnaryOperator op, int... instructions) {
        int counter = 0;
        for (int i = 0, j; i < instructions.length; i += j, counter++) {
            j = instructions[i];
            instructions[i] = op.applyAsInt(instructions[i]);
        }
        return counter;
    }
}
