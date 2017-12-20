package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day19.getPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day19Test {

    @Test
    public void testGetPath() {
        assertThat(getPath(EXAMPLE).getPath(), equalTo("ABCDEF"));
        assertThat(getPath(INPUT).getPath(), equalTo("DTOUFARJQ"));
    }

    @Test
    public void testGetNumberOfSteps() {
        assertThat(getPath(EXAMPLE).getNumberOfSteps(), equalTo(38));
        assertThat(getPath(INPUT).getNumberOfSteps(), equalTo(16642));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "     |          ",
            "     |  +--+    ",
            "     A  |  C    ",
            " F---|----E|--+ ",
            "     |  |  |  D ",
            "     +B-+  +--+ "
    );

    private static final List<String> INPUT = getInput();

    private static List<String> getInput() {
        try {
            return Files.readAllLines(Paths.get("out", "test", "resources", "Day19.txt"));
        } catch (IOException e) {
            throw new UnexpectedException(e.getMessage());
        }
    }
}