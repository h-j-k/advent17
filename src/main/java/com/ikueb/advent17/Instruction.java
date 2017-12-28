package com.ikueb.advent17;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Instruction implements BiFunction<Map<Character, Long>, String, Long> {
    SND((map, i) -> getValue(map, "  " + i)),
    SET((map, i) -> map.put(getRegister(i), getValue(map, i))),
    ADD((map, i) -> map.merge(getRegister(i), getValue(map, i), Math::addExact)),
    SUB((map, i) -> map.compute(getRegister(i),
            (k, v) -> (v == null ? 0 : v) - getValue(map, i))),
    MUL((map, i) -> map.compute(getRegister(i),
            (k, v) -> v == null ? 0 : v * getValue(map, i))),
    MOD((map, i) -> map.compute(getRegister(i),
            (k, v) -> v == null ? 0 : v % getValue(map, i))),
    RCV((map, i) -> Optional.ofNullable(map.get(getRegister(i)))
            .filter(v -> v != 0).orElse(null)),
    JGZ((map, i) -> Optional.of(getValue(map, "  " + i))
            .filter(v -> v > 0).map(v -> getValue(map, i)).orElse(null)),
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

    boolean isJumpInstruction() {
        return this == JGZ || this == JNZ;
    }

    private static char getRegister(String instruction) {
        return instruction.charAt(0);
    }

    private static long getValue(Map<Character, Long> map, String instruction) {
        return Character.isAlphabetic(instruction.charAt(2))
                ? map.getOrDefault(instruction.charAt(2), 0L)
                : Long.parseLong(instruction.substring(2).replaceFirst(" .*$", ""));
    }

    static InstructionResult compute(Map<Character, Long> map, String instruction) {
        Instruction current = INSTRUCTIONS.get(instruction.substring(0, 3));
        return new InstructionResult(current, instruction.charAt(4),
                current.apply(map, instruction.substring(4)));
    }
}
