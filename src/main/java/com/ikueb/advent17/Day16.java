package com.ikueb.advent17;

import java.util.HashMap;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Day16 {

    private static final Pattern SPLITTER = Pattern.compile(",");

    private Day16() {
        // empty
    }

    static String dance(String input, String instructions) {
        return dance(input, SPLITTER.splitAsStream(instructions));
    }

    private static String dance(String input, Stream<String> stream) {
        return stream.reduce(input, (programs, instruction) ->
                Instruction.get(instruction.charAt(0))
                        .apply(programs, instruction.substring(1)));
    }

    static String danceTillDrop(String input, String instructions) {
        var instructionList = SPLITTER.splitAsStream(instructions)
                .collect(Collectors.toUnmodifiableList());
        var cache = new HashMap<String, String>();
        var previous = input;
        for (var i = 0; i < 1_000_000_000; i++) {
            previous = cache.computeIfAbsent(previous,
                    k -> dance(k, instructionList.stream()));
        }
        return previous;
    }

    private enum Instruction implements BinaryOperator<String> {
        SPIN((programs, instruction) -> {
            var offset = programs.length() - Integer.parseInt(instruction);
            return programs.substring(offset) + programs.substring(0, offset);
        }),
        EXCHANGE((programs, instruction) -> {
            var slash = instruction.indexOf('/');
            var aIndex = Integer.parseInt(instruction.substring(0, slash));
            var bIndex = Integer.parseInt(instruction.substring(1 + slash));
            return swap(programs, aIndex, bIndex);
        }),
        PARTNER((programs, instruction) -> {
            var aIndex = programs.indexOf(instruction.charAt(0));
            var bIndex = programs.indexOf(instruction.charAt(2));
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
                    throw new UnexpectedException("value of: sX, xA/B, pA/B");
            }
        }

        private static String swap(String programs, int aIndex, int bIndex) {
            var chars = programs.toCharArray();
            var temp = chars[aIndex];
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
