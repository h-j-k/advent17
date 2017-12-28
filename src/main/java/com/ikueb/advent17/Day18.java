package com.ikueb.advent17;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

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
            result = new Result(Instruction.compute(map, temp.get(i)));
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
                result = new Result(Instruction.compute(map, temp.get(i)));
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

    private static final class Result extends InstructionResult {

        private Result(InstructionResult result) {
            super(result.getInstruction(), result.getRegister(), result.getValue());
        }

        boolean isPlayed() {
            return getInstruction() == Instruction.SND;
        }

        boolean isSending() {
            return getInstruction() == Instruction.SND;
        }

        boolean isRecovered() {
            return getInstruction() == Instruction.RCV && value != null;
        }

        boolean isReceiving() {
            return getInstruction() == Instruction.RCV;
        }
    }
}
