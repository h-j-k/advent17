package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day19.getPath;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day19Test {

    @Test
    public void testExample() {
        Day19.Result result = getPath(EXAMPLE);
        assertThat(result.getPath(), equalTo("ABCDEF"));
        assertThat(result.getNumberOfSteps(), equalTo(38));
    }

    @Test
    public void testInput() {
        Day19.Result result = getPath(getInput(Day19.class));
        assertThat(result.getPath(), equalTo("DTOUFARJQ"));
        assertThat(result.getNumberOfSteps(), equalTo(16642));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "     |          ",
            "     |  +--+    ",
            "     A  |  C    ",
            " F---|----E|--+ ",
            "     |  |  |  D ",
            "     +B-+  +--+ "
    );
}