package com.ikueb.advent17;

import java.util.BitSet;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

final class Day14 {

    private static final Pattern SPLITTER = Pattern.compile("");

    private Day14() {
        // empty
    }

    static int usedSquares(String input) {
        return IntStream.range(0, 128)
                .mapToObj(i -> input + "-" + i)
                .map(Day10::hash)
                .flatMap(SPLITTER::splitAsStream)
                .map(v -> new long[]{Long.parseLong(v, 16)})
                .map(BitSet::valueOf)
                .mapToInt(BitSet::cardinality)
                .sum();
    }
}
