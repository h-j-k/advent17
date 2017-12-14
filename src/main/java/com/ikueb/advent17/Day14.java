package com.ikueb.advent17;

import java.util.BitSet;
import java.util.stream.IntStream;

final class Day14 {

    private Day14() {
        // empty
    }

    static int usedSquares(String input) {
        return IntStream.range(0, 128)
                .mapToObj(i -> input + "-" + i)
                .map(Day10::hash)
                .map(Day14::asBitSet)
                .mapToInt(BitSet::cardinality)
                .sum();
    }

    private static BitSet asBitSet(String hash) {
        return BitSet.valueOf(IntStream.range(0, 8)
                .mapToObj(i -> hash.substring(i * 4, (i + 1) * 4))
                .mapToLong(v -> Long.parseLong(v, 16))
                .toArray());
    }
}
