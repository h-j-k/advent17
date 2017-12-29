package com.ikueb.advent17;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

    static long getH(int value) {
        // with lots of help from reddit
        int seed = value * 100 + 100000;
        return IntStream.iterate(0, i -> i <= 1000, i -> i + 1)
                .filter(i -> !BigInteger.valueOf(seed + 17 * i).isProbablePrime(100))
                .count();
    }
}
