package com.ikueb.advent17;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

final class Day18 {

    private Day18() {
        // empty
    }

    static long recoverFrequency(List<String> instructions) {
        long lastPlayed = -1;
        Map<Character, Long> map = new HashMap<>();
        List<String> temp = new ArrayList<>(instructions);
        Result result;
        for (int i = 0; ; i += result.jumpToNextInstruction()) {
            result = Instruction.compute(map, temp.get(i));
            if (result.isPlayed()) {
                lastPlayed = result.getResult();
            } else if (result.isRecovered()) {
                return lastPlayed;
            }
        }
    }

    private static final class Result {
        private final Instruction instruction;
        private final Long result;

        private Result(Instruction instruction, Long result) {
            this.instruction = instruction;
            this.result = result;
        }

        long getResult() {
            return Optional.ofNullable(result).orElseThrow(
                    () -> new UnexpectedException("Expecting a result."));
        }

        boolean isPlayed() {
            return instruction == Instruction.SND;
        }

        boolean isRecovered() {
            return instruction == Instruction.RCV && result != null;
        }

        int jumpToNextInstruction() {
            return instruction == Instruction.JGZ && result != null
                    ? result.intValue() : 1;
        }
    }

    private enum Instruction
            implements BiFunction<Map<Character, Long>, String, Long> {
        SND((map, i) -> map.get(getRegister(i))),
        SET((map, i) -> map.put(getRegister(i), getValue(map, i))),
        ADD((map, i) -> map.merge(getRegister(i), getValue(map, i), Math::addExact)),
        MUL((map, i) -> map.compute(getRegister(i),
                (k, v) -> v == null ? 0 : v * getValue(map, i))),
        MOD((map, i) -> map.compute(getRegister(i),
                (k, v) -> v == null ? 0 : v % getValue(map, i))),
        RCV((map, i) -> Optional.ofNullable(map.get(getRegister(i)))
                .filter(v -> v != 0).orElse(null)),
        JGZ((map, i) -> Optional.ofNullable(map.get(getRegister(i)))
                .filter(v -> v > 0).map(v -> getValue(map, i)).orElse(null));

        private static final Map<String, Instruction> INSTRUCTIONS = Arrays.stream(values())
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Instruction::getKey, Function.identity()),
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
                    : Long.parseLong(instruction.substring(2));
        }

        static Result compute(Map<Character, Long> map, String instruction) {
            Instruction current = INSTRUCTIONS.get(instruction.substring(0, 3));
            return new Result(current, current.apply(map, instruction.substring(4)));
        }
    }
}
