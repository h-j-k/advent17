package com.ikueb.advent17;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

final class Day23 {

    private Day23() {
        // empty
    }

    static int getInvocationCount(List<String> inputs,
                                  Instruction instruction) {
        int counter = 0;
        Map<Character, Long> map = new HashMap<>();
        List<String> temp = new ArrayList<>(inputs);
        Result result;
        for (int i = 0; i < temp.size(); i += result.jumpToNextInstruction()) {
            result = Instruction.compute(map, temp.get(i));
            if (result.getInstruction() == instruction) {
                counter++;
            }
        }
        return counter;
    }

    private static final class Result {
        private final Instruction instruction;
        private final Long value;

        private Result(Instruction instruction, Long value) {
            this.instruction = instruction;
            this.value = value;
        }

        Instruction getInstruction() {
            return instruction;
        }

        int jumpToNextInstruction() {
            return instruction == Instruction.JNZ && value != null
                    ? value.intValue() : 1;
        }
    }

    enum Instruction implements BiFunction<Map<Character, Long>, String, Long> {
        SET((map, i) -> map.put(getRegister(i), getValue(map, i))),
        SUB((map, i) -> map.compute(getRegister(i),
                (k, v) -> (v == null ? 0 : v) - getValue(map, i))),
        MUL((map, i) -> map.compute(getRegister(i),
                (k, v) -> v == null ? 0 : v * getValue(map, i))),
        JNZ((map, i) -> Optional.of(getValue(map, "  " + i))
                .filter(v -> v != 0).map(v -> getValue(map, i)).orElse(null));

        private static final Map<String, Instruction> INSTRUCTIONS =
                Arrays.stream(values())
                        .collect(Collectors.collectingAndThen(
                                Collectors.toMap(
                                        Instruction::getKey,
                                        Function.identity()),
                                Collections::unmodifiableMap));

        private final BiFunction<Map<Character, Long>, String, Long> op;

        Instruction(BiFunction<Map<Character, Long>, String, Long> op) {
            this.op = op;
        }

        @Override
        public Long apply(Map<Character, Long> map, String instruction) {
            return op.apply(map, instruction);
        }

        private String getKey() {
            return name().toLowerCase();
        }

        private static char getRegister(String instruction) {
            return instruction.charAt(0);
        }

        private static long getValue(Map<Character, Long> map, String instruction) {
            return Character.isAlphabetic(instruction.charAt(2))
                    ? map.getOrDefault(instruction.charAt(2), 0L)
                    : Long.parseLong(instruction.substring(2).replaceFirst(" .*$", ""));
        }

        static Result compute(Map<Character, Long> map, String instruction) {
            Instruction current = INSTRUCTIONS.get(instruction.substring(0, 3));
            return new Result(current, current.apply(map, instruction.substring(4)));
        }
    }
}
