package com.ikueb.advent17;

import java.util.Arrays;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.function.ToIntFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class Day2 {

    private Day2() {
        // empty
    }

    static int checksumInput(Collection<String> input,
                             ToIntFunction<int[]> function) {
        return checksum(input.stream()
                .map(Pattern.compile("\\s+")::splitAsStream)
                .map(s -> s.mapToInt(Integer::parseInt).toArray())
                .collect(Collectors.toList()), function);
    }

    private static int checksum(Collection<int[]> matrix,
                                ToIntFunction<int[]> function) {
        return matrix.stream()
                .mapToInt(function)
                .sum();
    }

    static int diff(int... values) {
        IntSummaryStatistics stats = Arrays.stream(values).summaryStatistics();
        return stats.getMax() - stats.getMin();
    }

    static int evenlyDivisible(int... values) {
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                int a = values[i];
                int b = values[j];
                if (a % b == 0 || b % a == 0) {
                    return a > b ? a / b : b / a;
                }
            }
        }
        throw new UnexpectedException("There must be only one evenly divisible pair");
    }
}
