package com.ikueb.advent17;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
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
                lastPlayed = result.getValue();
            } else if (result.isRecovered()) {
                return lastPlayed;
            }
        }
    }

    static int getNumberOfSends(List<String> instructions) {
        Program zero = new Program(0, instructions);
        Program one = new Program(1, instructions).link(zero);
        return CompletableFuture.allOf(
                CompletableFuture.runAsync(zero),
                CompletableFuture.runAsync(one))
                .thenApply(aVoid -> one.getCounter()).join();
    }

    private static final class Program implements Runnable {

        private static final int TIMEOUT = 3;
        private final Map<Character, Long> map;
        private final List<String> temp;
        private final BlockingDeque<Long> queue = new LinkedBlockingDeque<>();
        private BlockingDeque<Long> otherQueue;
        private int counter = 0;

        private Program(long p, List<String> instructions) {
            this.map = new HashMap<>(Collections.singletonMap('p', p));
            this.temp = new ArrayList<>(instructions);
        }

        Program link(Program other) {
            other.otherQueue = this.queue;
            this.otherQueue = other.queue;
            return this;
        }

        public void run() {
            Result result;
            for (int i = 0; ; i += result.jumpToNextInstruction()) {
                result = Instruction.compute(map, temp.get(i));
                if (result.isSending() && queue.add(result.getValue())) {
                    counter++;
                } else if (result.isReceiving()) {
                    Long target = null;
                    try {
                        target = otherQueue.poll(TIMEOUT, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    if (target == null) {
                        return;
                    }
                    map.put(result.getRegister(), target);
                }
            }
        }

        int getCounter() {
            return counter;
        }
    }

    private static final class Result {
        private final Instruction instruction;
        private final char register;
        private final Long value;

        private Result(Instruction instruction, char register, Long value) {
            this.instruction = instruction;
            this.register = register;
            this.value = value;
        }

        char getRegister() {
            return register;
        }

        long getValue() {
            return value;
        }

        boolean isPlayed() {
            return instruction == Instruction.SND;
        }

        boolean isSending() {
            return instruction == Instruction.SND;
        }

        boolean isRecovered() {
            return instruction == Instruction.RCV && value != null;
        }

        boolean isReceiving() {
            return instruction == Instruction.RCV;
        }

        int jumpToNextInstruction() {
            return instruction == Instruction.JGZ && value != null
                    ? value.intValue() : 1;
        }
    }

    private enum Instruction
            implements BiFunction<Map<Character, Long>, String, Long> {
        SND((map, i) -> getValue(map, "  " + i)),
        SET((map, i) -> map.put(getRegister(i), getValue(map, i))),
        ADD((map, i) -> map.merge(getRegister(i), getValue(map, i), Math::addExact)),
        MUL((map, i) -> map.compute(getRegister(i),
                (k, v) -> v == null ? 0 : v * getValue(map, i))),
        MOD((map, i) -> map.compute(getRegister(i),
                (k, v) -> v == null ? 0 : v % getValue(map, i))),
        RCV((map, i) -> Optional.ofNullable(map.get(getRegister(i)))
                .filter(v -> v != 0).orElse(null)),
        JGZ((map, i) -> Optional.of(getValue(map, "  " + i))
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
                    : Long.parseLong(instruction.substring(2).replaceFirst(" .*$", ""));
        }

        static Result compute(Map<Character, Long> map, String instruction) {
            Instruction current = INSTRUCTIONS.get(instruction.substring(0, 3));
            return new Result(current, instruction.charAt(4),
                    current.apply(map, instruction.substring(4)));
        }
    }
}
