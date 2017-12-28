package com.ikueb.advent17;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class Day24 {

    private Day24() {
        // empty
    }

    static Result findStrongestBridge(List<String> inputs,
                                      Comparator<Result> comparator) {
        Map<Integer, Set<Bridge>> bridges = inputs.stream()
                .map(Bridge::from)
                .flatMap(Bridge::stream)
                .collect(Collectors.groupingBy(
                        Entry::getKey,
                        Collectors.mapping(Entry::getValue, Collectors.toSet())));
        return findMax(comparator, bridges, 0);
    }

    private static Result findMax(Comparator<Result> comparator,
                                  Map<Integer, Set<Bridge>> bridges,
                                  int side) {
        Result result = Result.START;
        for (Bridge bridge : bridges.getOrDefault(side, Collections.emptySet())) {
            if (!bridge.isUsed()) {
                bridge.setUsed(true);
                result = Result.max(comparator, result, findMax(
                        comparator, bridges, bridge.getOther(side)).addWeight(bridge));
                bridge.setUsed(false);
            }
        }
        return result;
    }

    static final class Result {
        private static final Result START = new Result(0, 0);
        static Comparator<Result> WEIGHT = Comparator.comparing(Result::getWeight);
        static Comparator<Result> LENGTH_THEN_WEIGHT =
                Comparator.comparing(Result::getLength)
                        .thenComparing(Result::getWeight);

        private final int weight;
        private final int length;

        Result(int weight, int length) {
            this.weight = weight;
            this.length = length;
        }

        int getWeight() {
            return weight;
        }

        int getLength() {
            return length;
        }

        int getResult() {
            return weight;
        }

        Result addWeight(Bridge bridge) {
            return new Result(weight + bridge.getWeight(), length + 1);
        }

        static Result max(Comparator<Result> comparator, Result a, Result b) {
            return comparator.compare(a, b) < 0 ? b : a;
        }
    }

    private static final class Bridge {
        private final int input;
        private final int output;
        private final int weight;
        private boolean isUsed = false;

        private Bridge(int input, int output) {
            this.input = input;
            this.output = output;
            this.weight = input + output;
        }

        int getInput() {
            return input;
        }

        int getOutput() {
            return output;
        }

        int getWeight() {
            return weight;
        }

        boolean isUsed() {
            return isUsed;
        }

        void setUsed(boolean isUsed) {
            this.isUsed = isUsed;
        }

        int getOther(int from) {
            return from == input ? output : input;
        }

        Stream<Entry<Integer, Bridge>> stream() {
            return IntStream.of(input, output)
                    .distinct()
                    .boxed()
                    .collect(Collectors.toMap(Function.identity(), v -> this))
                    .entrySet()
                    .stream();
        }

        static Bridge from(String input) {
            String[] parts = input.split("/");
            return new Bridge(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Bridge
                    && input == ((Bridge) o).input
                    && output == ((Bridge) o).output;
        }

        @Override
        public int hashCode() {
            return Objects.hash(input, output);
        }
    }
}
