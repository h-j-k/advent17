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

    static int findStrongestBridge(List<String> inputs) {
        Map<Integer, Set<Bridge>> connectors = inputs.stream()
                .map(Bridge::from)
                .flatMap(Bridge::stream)
                .collect(Collectors.groupingBy(
                        Entry::getKey,
                        Collectors.mapping(Entry::getValue, Collectors.toSet())));
        removeWeakLinks(connectors);
        return findMax(connectors, 0);
    }

    private static void removeWeakLinks(Map<Integer, Set<Bridge>> connectors) {
        connectors.values().forEach(set ->
                set.removeIf(bridge ->
                        IntStream.of(bridge.getInput(), bridge.getOutput())
                                .anyMatch(i -> connectors.get(i).size() == 1)));
        new HashSet<>(connectors.keySet()).forEach(end ->
                connectors.compute(end, (k, v) -> v.isEmpty() ? null : v));
    }

    private static int findMax(Map<Integer, Set<Bridge>> connectors,
                               int side) {
        int result = 0;
        for (Bridge bridge : connectors.getOrDefault(side, Collections.emptySet())) {
            if (!bridge.isUsed()) {
                bridge.setUsed(true);
                result = Math.max(result, bridge.getWeight()
                        + findMax(connectors, bridge.getOther(side)));
                bridge.setUsed(false);
            }
        }
        return result;
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
