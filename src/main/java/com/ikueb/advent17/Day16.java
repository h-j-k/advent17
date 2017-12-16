package com.ikueb.advent17;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;

final class Day16 {

    private static final Pattern SPLITTER = Pattern.compile(",");

    private Day16() {
        // empty
    }

    static String dance(String input, String instructions) {
        return SPLITTER.splitAsStream(instructions)
                .reduce(input, (programs, instruction) ->
                        Instruction.get(instruction.charAt(0))
                                .apply(programs, instruction.substring(1)));
    }

    static String danceTillDrop(String input, String instructions) {
        Map<String, String> cache = new HashMap<>();
        String previous = input;
        for (int i = 0; i < 1_000_000_000; i++) {
            previous = cache.computeIfAbsent(previous, k -> dance(k, instructions));
        }
        return previous;
    }

    private enum Instruction implements BinaryOperator<String> {
        SPIN((programs, instruction) -> {
            int offset = programs.length() - Integer.parseInt(instruction);
            return programs.substring(offset) + programs.substring(0, offset);
        }),
        EXCHANGE((programs, instruction) -> {
            int slash = instruction.indexOf('/');
            int aIndex = Integer.parseInt(instruction.substring(0, slash));
            int bIndex = Integer.parseInt(instruction.substring(1 + slash));
            return swap(programs, aIndex, bIndex);
        }),
        PARTNER((programs, instruction) -> {
            int aIndex = programs.indexOf(instruction.charAt(0));
            int bIndex = programs.indexOf(instruction.charAt(2));
            return swap(programs, aIndex, bIndex);
        });

        private final BinaryOperator<String> operator;

        Instruction(BinaryOperator<String> operator) {
            this.operator = operator;
        }

        static Instruction get(char prefix) {
            switch (prefix) {
                case 's':
                    return SPIN;
                case 'x':
                    return EXCHANGE;
                case 'p':
                    return PARTNER;
                default:
                    throw new IllegalArgumentException(
                            "Should be one of: sX, xA/B, pA/B");
            }
        }

        private static String swap(String programs, int aIndex, int bIndex) {
            char[] chars = programs.toCharArray();
            char temp = chars[aIndex];
            chars[aIndex] = chars[bIndex];
            chars[bIndex] = temp;
            return new String(chars);
        }

        @Override
        public String apply(String programs, String instruction) {
            return operator.apply(programs, instruction);
        }
    }
}
