package com.ikueb.advent17;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

final class Day15 {

    private Day15() {
        // empty
    }

    static long getMatchingLowest16Digits(int aSeed, int bSeed) {
        return Stream.iterate(GeneratorPair.of(aSeed, bSeed), GeneratorPair::next)
                .limit(40_000_000)
                .filter(GeneratorPair::matches)
                .count();
    }

    private static final class GeneratorPair {

        private static final int A_MULTIPLIER = 16807;
        private static final int B_MULTIPLIER = 48271;
        private static final IntBinaryOperator OP =
                (x, y) -> (int) (((long) x * y) % Integer.MAX_VALUE);
        private static final IntUnaryOperator A_OP =
                a -> OP.applyAsInt(a, A_MULTIPLIER);
        private static final IntUnaryOperator B_OP =
                b -> OP.applyAsInt(b, B_MULTIPLIER);
        private static final IntUnaryOperator EXTRACTOR =
                x -> x % 65536;

        private final int a;
        private final int b;

        private GeneratorPair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        GeneratorPair next() {
            return of(a, b);
        }

        boolean matches() {
            return EXTRACTOR.applyAsInt(a) == EXTRACTOR.applyAsInt(b);
        }

        static GeneratorPair of(int a, int b) {
            return new GeneratorPair(A_OP.applyAsInt(a), B_OP.applyAsInt(b));
        }

        @Override
        public String toString() {
            return a + ", " + b;
        }
    }
}
