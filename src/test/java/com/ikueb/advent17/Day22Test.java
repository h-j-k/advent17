package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day22.countInfectedNodes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day22Test {

    @Test
    public void testCountInfectedNodes() {
        assertThat(countInfectedNodes(EXAMPLE, 10_000), equalTo(5587));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "..#",
            "#..",
            "..."
    );
}