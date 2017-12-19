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
        assertThat(getPath(EXAMPLE), equalTo("ABCDE"));
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("out", "test", "resources", "Day19.txt"));
        } catch (IOException e) {
            throw new UnexpectedException(e.getMessage());
        }
        assertThat(getPath(lines), equalTo("HJK"));
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