package com.ikueb.advent17;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Day25 {

    private static final Pattern EXTRACTOR =
            Pattern.compile("^.* ([0-9A-Z]+|left|right)(?: steps)?[.:]$");

    static long getDiagnosticChecksum(List<String> inputs) {
        Iterator<String> iterator = inputs.iterator();
        char currentState = getCharacter(iterator.next());
        int iterations = getNumber(iterator.next());
        Map<Character, State> states = getStateMap(iterator);
        Map<Integer, Integer> results = new HashMap<>(Collections.singletonMap(0, 0));
        int current = 0;
        for (int i = 0; i < iterations; i++) {
            Step step = states.get(currentState).getStepFor(results.get(current));
            results.put(current, step.newValue);
            current += step.shiftBy;
            results.putIfAbsent(current, 0);
            currentState = step.nextState;
        }
        return results.values().stream().filter(i -> i == 1).count();
    }

    private static Map<Character, State> getStateMap(Iterator<String> iterator) {
        Map<Character, State> map = new HashMap<>();
        while (iterator.hasNext()) {
            iterator.next();
            char name = getCharacter(iterator.next());
            iterator.next();
            Step ifZero = Step.create(iterator);
            iterator.next();
            Step ifOne = Step.create(iterator);
            map.put(name, new State(ifZero, ifOne));
        }
        return map;
    }

    private static int getNumber(String input) {
        return Integer.parseInt(getValue(input));
    }

    private static char getCharacter(String input) {
        return getValue(input).charAt(0);
    }

    private static String getValue(String input) {
        Matcher matcher = EXTRACTOR.matcher(input);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        throw new UnexpectedException("Expecting a value.");
    }

    private static final class Step {
        final int newValue;
        final int shiftBy;
        final char nextState;

        private Step(int newValue, int shiftBy, char nextState) {
            this.newValue = newValue;
            this.shiftBy = shiftBy;
            this.nextState = nextState;
        }

        static Step create(Iterator<String> iterator) {
            return new Step(
                    getNumber(iterator.next()),
                    getValue(iterator.next()).equals("left") ? -1 : 1,
                    getCharacter(iterator.next()));
        }
    }

    private static final class State {
        private final Step ifZero;
        private final Step ifOne;

        State(Step ifZero, Step ifOne) {
            this.ifZero = ifZero;
            this.ifOne = ifOne;
        }

        Step getStepFor(int current) {
            return current == 0 ? ifZero : ifOne;
        }
    }
}
