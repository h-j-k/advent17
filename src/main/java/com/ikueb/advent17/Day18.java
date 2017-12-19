package com.ikueb.advent17;

import java.util.*;
import java.util.concurrent.*;
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

    static int getSends(List<String> instructions) {
        Program zero = new Program(0, instructions);
        Program one = new Program(1, instructions).link(zero);
        ExecutorService service = Executors.newFixedThreadPool(2);
        return CompletableFuture.allOf(
                CompletableFuture.runAsync(zero, service),
                CompletableFuture.runAsync(one, service))
                .thenApply(aVoid -> one.getCounter()).join();
    }

    private static final class Program implements Runnable {

        private static final int TIMEOUT = 3;
        private final long p;
        private final Map<Character, Long> map;
        private final List<String> temp;
        private final BlockingDeque<Long> queue = new LinkedBlockingDeque<>();
        private BlockingDeque<Long> otherQueue;
        private int counter = 0;

        private Program(long p, List<String> instructions) {
            this.p = p;
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
                if (result.isSending()) {
                    queue.offer(result.getResult());
                    counter++;
                } else if (result.isReceiving()) {
                    Long target;
                    try {
                        target = otherQueue.poll(TIMEOUT, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        return;
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
        private final Long result;

        private Result(Instruction instruction, char register, Long result) {
            this.instruction = instruction;
            this.register = register;
            this.result = result;
        }

        char getRegister() {
            return register;
        }

        long getResult() {
            return Optional.ofNullable(result).orElseThrow(
                    () -> new UnexpectedException("Expecting a result."));
        }

        boolean isPlayed() {
            return instruction == Instruction.SND;
        }

        boolean isSending() {
            return instruction == Instruction.SND;
        }

        boolean isRecovered() {
            return instruction == Instruction.RCV && result != null;
        }

        boolean isReceiving() {
            return instruction == Instruction.RCV;
        }

        int jumpToNextInstruction() {
            return instruction == Instruction.JGZ && result != null
                    ? result.intValue() : 1;
        }

        @Override
        public String toString() {
            return instruction.name() + " " + register + " " + result;
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
