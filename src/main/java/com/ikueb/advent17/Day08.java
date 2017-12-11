package com.ikueb.advent17;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class Day08 {

    private static final String REGISTER =
            "(?<reg>[a-z]+) (?<regOp>(inc|dec)) (?<regVal>[0-9-]+)";

    private static final String COND =
            "(?<cond>[a-z]+) (?<condOp>(<|>|<=|>=|==|!=)) (?<condVal>[0-9-]+)";

    private static final Pattern PARSER = Pattern.compile(REGISTER + " if " + COND);

    private Day08() {
        // empty
    }

    static int getLargestRegister(List<String> instructions) {
        return process(instructions, value -> { /* */ }).values().stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow(() -> new UnexpectedException("Expecting a result"));
    }

    static int getLargestRegisterEver(List<String> instructions) {
        int[] result = {Integer.MIN_VALUE};
        process(instructions, value -> {
            if (value > result[0]) {
                result[0] = value;
            }
        });
        return result[0];
    }

    private static Map<String, Integer> process(List<String> instructions,
                                                Consumer<Integer> consumer) {
        Map<String, Integer> registers = new HashMap<>();
        instructions.stream()
                .map(PARSER::matcher)
                .filter(Matcher::matches)
                .forEach(i -> {
                    Integer cond = registers.computeIfAbsent(i.group("cond"), k -> 0);
                    ConditionOp condOp = ConditionOp.parse(i.group("condOp"));
                    Integer condVal = Integer.valueOf(i.group("condVal"));
                    if (condOp.test(cond, condVal)) {
                        String reg = i.group("reg");
                        RegisterOp regOp = RegisterOp.parse(i.group("regOp"));
                        int regVal = Integer.parseInt(i.group("regVal"));
                        consumer.accept(registers.compute(reg, (k, v) ->
                                regOp.applyAsInt(v == null ? 0 : v, regVal)));
                    }
                });
        return registers;
    }

    private enum RegisterOp implements IntBinaryOperator {
        INC(Math::addExact), DEC(Math::subtractExact);

        private final IntBinaryOperator op;

        private static final Map<String, RegisterOp> LOOKUP = Arrays.stream(values())
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(RegisterOp::key, Function.identity()),
                        Collections::unmodifiableMap));

        RegisterOp(IntBinaryOperator op) {
            this.op = op;
        }

        private String key() {
            return name().toLowerCase();
        }

        @Override
        public int applyAsInt(int left, int right) {
            return op.applyAsInt(left, right);
        }

        static RegisterOp parse(String op) {
            return LOOKUP.get(op);
        }
    }

    private enum ConditionOp implements BiPredicate<Integer, Integer> {
        LESS_THAN("<", (a, b) -> a < b),
        GREATER_THAN(">", (a, b) -> a > b),
        LESS_THAN_OR_EQUAL("<=", (a, b) -> a <= b),
        GREATER_THAN_OR_EQUAL(">=", (a, b) -> a >= b),
        EQUAL("==", Integer::equals),
        NOT_EQUAL("!=", (a, b) -> !a.equals(b));

        private final String key;
        private final BiPredicate<Integer, Integer> op;

        private static final Map<String, ConditionOp> LOOKUP = Arrays.stream(values())
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(ConditionOp::key, Function.identity()),
                        Collections::unmodifiableMap));

        ConditionOp(String key, BiPredicate<Integer, Integer> op) {
            this.key = key;
            this.op = op;
        }

        private String key() {
            return key;
        }

        @Override
        public boolean test(Integer left, Integer right) {
            return op.test(left, right);
        }

        static ConditionOp parse(String op) {
            return LOOKUP.get(op);
        }
    }
}
