package com.ikueb.advent17;

import java.util.function.IntUnaryOperator;

class Day5 {

    private Day5() {
        // empty
    }

    static int getSteps(IntUnaryOperator op, int... instructions) {
        int counter = 0;
        for (int i = 0; i < instructions.length; counter++) {
            int jump = instructions[i];
            instructions[i] = op.applyAsInt(instructions[i]);
            i += jump;
        }
        return counter;
    }
}
