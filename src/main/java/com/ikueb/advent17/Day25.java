package com.ikueb.advent17;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class Day25 {

    private static final Pattern EXTRACTOR =
            Pattern.compile("^.* ([0-9A-Z]+|left|right)(?: steps)?[.:]$");

    private Day25() {
        // empty
    }

    static long getDiagnosticChecksum(List<String> inputs) {
        var iterator = inputs.iterator();
        var stateName = getCharacter(iterator.next());
        var iterations = getNumber(iterator.next());
        var states = getStateMap(iterator);
        var results = new HashMap<>(Map.of(0, 0));
        var index = 0;
        for (var i = 0; i < iterations; i++) {
            var current = states.get(stateName);
            var value = results.getOrDefault(index, 0);
            stateName = current.nextStateName(value);
            var shiftBy = current.shiftBy(value);
            results.compute(index, current);
            index += shiftBy;
        }
        return results.values().stream().filter(i -> i == 1).count();
    }

    private static Map<Character, State> getStateMap(Iterator<String> iterator) {
        var map = new HashMap<Character, State>();
        while (iterator.hasNext()) {
            iterator.next();
            map.put(getCharacter(iterator.next()),
                    new State(Step.create(iterator), Step.create(iterator)));
        }
        return map;
    }

    private static char getCharacter(String input) {
        return getValue(input).charAt(0);
    }

    private static int getNumber(String input) {
        return Integer.parseInt(getValue(input));
    }

    private static String getValue(String input) {
        var matcher = EXTRACTOR.matcher(input);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        throw new UnexpectedException("value");
    }

    private static final class Step {
        final int newValue;
        final int shiftBy;
        final char nextStateName;

        private Step(int newValue, int shiftBy, char nextStateName) {
            this.newValue = newValue;
            this.shiftBy = shiftBy;
            this.nextStateName = nextStateName;
        }

        static Map<Integer, Step> create(Iterator<String> iterator) {
            return Map.of(getNumber(iterator.next()), new Step(
                    getNumber(iterator.next()),
                    getValue(iterator.next()).equals("left") ? -1 : 1,
                    getCharacter(iterator.next())));
        }
    }

    private static final class State implements BiFunction<Integer, Integer, Integer> {
        private final Map<Integer, Step> conditions;

        @SafeVarargs
        State(Map<Integer, Step>... conditions) {
            this.conditions = Arrays.stream(conditions)
                    .flatMap(m -> m.entrySet().stream())
                    .collect(Collectors.toUnmodifiableMap(Entry::getKey, Entry::getValue));
        }

        int shiftBy(int value) {
            return conditions.get(value).shiftBy;
        }

        char nextStateName(int value) {
            return conditions.get(value).nextStateName;
        }

        @Override
        public Integer apply(Integer key, Integer value) {
            return conditions.get(value == null ? 0 : value).newValue;
        }
    }
}
