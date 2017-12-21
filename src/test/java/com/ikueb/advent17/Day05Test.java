package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.function.IntUnaryOperator;

import static com.ikueb.advent17.Day05.getSteps;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day05Test {

    @Test
    public void testPartOne() {
        assertThat(getSteps(EXAMPLE), equalTo(5));
        assertThat(getSteps(INSTRUCTIONS), equalTo(360603));
    }

    @Test
    public void testPartTwo() {
        IntUnaryOperator op = i -> i >= 3 ? i - 1 : i + 1;
        assertThat(getSteps(op, EXAMPLE), equalTo(10));
        assertThat(getSteps(op, INSTRUCTIONS), equalTo(25347697));
    }

    private static final int[] EXAMPLE = {0, 3, 0, 1, -3};

    private static final int[] INSTRUCTIONS = getInput(Day05.class).stream()
            .mapToInt(Integer::parseInt).toArray();
}