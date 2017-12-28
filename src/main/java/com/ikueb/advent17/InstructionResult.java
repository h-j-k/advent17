package com.ikueb.advent17;

class InstructionResult {

    final Instruction instruction;
    final char register;
    final Long value;

    public InstructionResult(Instruction instruction, char register, Long value) {
        this.instruction = instruction;
        this.register = register;
        this.value = value;
    }

    Instruction getInstruction() {
        return instruction;
    }

    char getRegister() {
        return register;
    }

    Long getValue() {
        return value;
    }

    int jumpToNextInstruction() {
        return instruction.isJumpInstruction() && value != null ? value.intValue() : 1;
    }
}
