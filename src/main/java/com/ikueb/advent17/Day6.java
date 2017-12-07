package com.ikueb.advent17;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

final class Day6 {

    private Day6() {
        // empty
    }

    static int getUniqueRedistribution(int... banks) {
        int counter = 0;
        Set<BanksWrapper> seen = new HashSet<>();
        for (BanksWrapper current = new BanksWrapper(banks);
             seen.add(current);
             current = current.reallocate()) {
            counter++;
        }
        return counter;
    }

    private static final class BanksWrapper {

        private final int[] banks;

        BanksWrapper(int[] banks) {
            this.banks = Arrays.copyOf(banks, banks.length);
        }

        BanksWrapper reallocate() {
            int[] copy = Arrays.copyOf(banks, banks.length);
            int n = copy.length;
            int max = IntStream.range(0, n)
                    .reduce(0, (a, b) -> copy[a] < copy[b] ? b : a);
            int reallocate = copy[max];
            copy[max] = 0;
            IntUnaryOperator increment = i -> i + 1 == n ? 0 : i + 1;
            for (int i = increment.applyAsInt(max); reallocate > 0;
                 i = increment.applyAsInt(i), reallocate--) {
                copy[i]++;
            }
            return new BanksWrapper(copy);
        }

        @Override
        public boolean equals(Object o) {
            return this == o || (o instanceof BanksWrapper
                    && Arrays.equals(banks, ((BanksWrapper) o).banks));
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(banks);
        }
    }
}
