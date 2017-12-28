package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ikueb.advent17.Day23.getInvocationCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day23Test {

    @Test
    public void testGetInvocationCount() {
        assertThat(getInvocationCount(INPUTS, Instruction.MUL), equalTo(6724));
    }

    private static final List<String> INPUTS = Arrays.asList(
            "set b 84",
            "set c b",
            "jnz a 2",
            "jnz 1 5",
            "mul b 100",
            "sub b -100000",
            "set c b",
            "sub c -17000",
            "set f 1",
            "set d 2",
            "set e 2",
            "set g d",
            "mul g e",
            "sub g b",
            "jnz g 2",
            "set f 0",
            "sub e -1",
            "set g e",
            "sub g b",
            "jnz g -8",
            "sub d -1",
            "set g d",
            "sub g b",
            "jnz g -13",
            "jnz f 2",
            "sub h -1",
            "set g b",
            "sub g c",
            "jnz g 2",
            "jnz 1 3",
            "sub b -17",
            "jnz 1 -23"
    );
}