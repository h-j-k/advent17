package com.ikueb.advent17;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

final class Day6 {

    private Day6() {
        // empty
    }

    static int getFirstDuplicateCount(int... banks) {
        return DuplicateCountResult.get(new BanksWrapper(banks)).getResult();
    }

    static int getSecondDuplicateCount(int... banks) {
        return DuplicateCountResult.get(
                DuplicateCountResult.get(new BanksWrapper(banks)).getBanksWrapper()).getResult();
    }

    private static final class DuplicateCountResult {
        private final BanksWrapper banksWrapper;
        private final int result;

        private DuplicateCountResult(BanksWrapper banksWrapper, int result) {
            this.banksWrapper = banksWrapper;
            this.result = result;
        }

        BanksWrapper getBanksWrapper() {
            return banksWrapper;
        }

        int getResult() {
            return result;
        }

        static DuplicateCountResult get(BanksWrapper banksWrapper) {
            Set<BanksWrapper> seen = new HashSet<>();
            BanksWrapper current = banksWrapper;
            int counter = 0;
            while (seen.add(current)) {
                counter++;
                current = current.reallocate();
            }
            return new DuplicateCountResult(current, counter);
        }
    }

    private static final class BanksWrapper {

        private final int[] banks;

        private BanksWrapper(int[] banks) {
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
