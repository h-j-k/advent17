package com.ikueb.advent17;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.LongStream;

final class Day23 {

    private Day23() {
        // empty
    }

    static int getInvocationCount(List<String> inputs,
                                  Instruction instruction) {
        var counter = 0;
        var map = new HashMap<Character, Long>();
        var temp = new ArrayList<>(inputs);
        InstructionResult result;
        for (var i = 0; i < temp.size(); i += result.jumpToNextInstruction()) {
            result = Instruction.compute(map, temp.get(i));
            if (result.getInstruction() == instruction) {
                counter++;
            }
        }
        return counter;
    }

    static long getH(int value) {
        // with lots of help from reddit
        var seed = value * 100 + 100000;
        return LongStream.iterate(0, i -> i <= 1000, i -> i + 1)
                .filter(i -> !BigInteger.valueOf(seed + 17 * i).isProbablePrime(100))
                .count();
    }
}
