package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day7.getBottomProgram;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day7Test {

    @Test
    public void testExample() {
        assertThat(getBottomProgram(EXAMPLE), equalTo("tknk"));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "pbga (66)",
            "xhth (57)",
            "ebii (61)",
            "havc (66)",
            "ktlj (57)",
            "fwft (72) -> ktlj, cntj, xhth",
            "qoyq (66)",
            "padx (45) -> pbga, havc, qoyq",
            "tknk (41) -> ugml, padx, fwft",
            "jptl (61)",
            "ugml (68) -> gyxo, ebii, jptl",
            "gyxo (61)",
            "cntj (57)"
    );

}