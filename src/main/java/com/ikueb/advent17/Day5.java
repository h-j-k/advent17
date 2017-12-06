package com.ikueb.advent17;

class Day5 {

    private Day5() {
        // empty
    }

    static int getSteps(int... instructions) {
        int counter = 0;
        for (int i = 0; i < instructions.length; counter++) {
            int jump = instructions[i];
            instructions[i]++;
            i += jump;
        }
        return counter;
    }
}
