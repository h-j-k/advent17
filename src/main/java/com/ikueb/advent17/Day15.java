package com.ikueb.advent17;

import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class Day15 {

    private Day15() {
        // empty
    }

    static long getMatchingLowest16Digits(int aSeed, int bSeed,
                                          UnaryOperator<GeneratorPair> generator,
                                          long limit) {
        return Stream.iterate(GeneratorPair.of(aSeed, bSeed), generator)
                .limit(limit)
                .filter(GeneratorPair::matches)
                .count();
    }

    static final class GeneratorPair {

        private static final IntBinaryOperator OP =
                (x, y) -> (int) (((long) x * y) % Integer.MAX_VALUE);
        private static final IntUnaryOperator A_OP = i -> OP.applyAsInt(i, 16807);
        private static final IntUnaryOperator B_OP = i -> OP.applyAsInt(i, 48271);
        private static final IntUnaryOperator EXTRACTOR = x -> x % 65536;

        private final int a;
        private final int b;

        private GeneratorPair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        GeneratorPair next() {
            return of(a, b);
        }

        GeneratorPair next(IntPredicate aPredicate, IntPredicate bPredicate) {
            return of(findFirst(IntStream.iterate(a, A_OP).filter(aPredicate)),
                    findFirst(IntStream.iterate(b, B_OP).filter(bPredicate)));
        }

        boolean matches() {
            return EXTRACTOR.applyAsInt(a) == EXTRACTOR.applyAsInt(b);
        }

        static GeneratorPair of(int a, int b) {
            return of(() -> A_OP.applyAsInt(a), () -> B_OP.applyAsInt(b));
        }

        static GeneratorPair of(IntSupplier aSupplier, IntSupplier bSupplier) {
            return new GeneratorPair(aSupplier.getAsInt(), bSupplier.getAsInt());
        }

        private static IntSupplier findFirst(IntStream stream) {
            return () -> stream.skip(1).findFirst()
                    .orElseThrow(() -> new UnexpectedException("Expecting a value."));
        }
    }
}
