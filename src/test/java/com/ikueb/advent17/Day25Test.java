package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day25.getDiagnosticChecksum;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day25Test {

    @Test
    public void testGetDiagnosticChecksum() {
        assertThat(getDiagnosticChecksum(EXAMPLE), equalTo(3L));
        assertThat(getDiagnosticChecksum(INPUT), equalTo(2846L));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "Begin in state A.",
            "Perform a diagnostic checksum after 6 steps.",
            "",
            "In state A:",
            "  If the current value is 0:",
            "    - Write the value 1.",
            "    - Move one slot to the right.",
            "    - Continue with state B.",
            "  If the current value is 1:",
            "    - Write the value 0.",
            "    - Move one slot to the left.",
            "    - Continue with state B.",
            "",
            "In state B:",
            "  If the current value is 0:",
            "    - Write the value 1.",
            "    - Move one slot to the left.",
            "    - Continue with state A.",
            "  If the current value is 1:",
            "    - Write the value 1.",
            "    - Move one slot to the right.",
            "    - Continue with state A."
    );

    private static final List<String> INPUT = getInput(Day25.class);
}