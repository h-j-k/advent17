package com.ikueb.advent17;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class Day23 {

    private Day23() {
        // empty
    }

    static int getInvocationCount(List<String> inputs,
                                  Instruction instruction) {
        int counter = 0;
        Map<Character, Long> map = new HashMap<>();
        List<String> temp = new ArrayList<>(inputs);
        InstructionResult result;
        for (int i = 0; i < temp.size(); i += result.jumpToNextInstruction()) {
            result = Instruction.compute(map, temp.get(i));
            if (result.getInstruction() == instruction) {
                counter++;
            }
        }
        return counter;
    }

    static int getH(int seed) {
        // with lots of help from reddit
        int counter = 0;
        int original = seed * 100 + 100000;
        for (int i = 0; i <= 1000; i++) {
            int number = original + 17 * i;
            if (!BigInteger.valueOf(number).isProbablePrime(100000)) {
                counter++;
            }
        }
        return counter;
    }
}
