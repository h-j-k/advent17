package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day21.countPixelsOnAfterIterations;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day21Test {

    @Test
    public void testCountPixelsOnAfterIterations() {
        assertThat(countPixelsOnAfterIterations(2, EXAMPLE), equalTo(12));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "../.# => ##./#../...",
            ".#./..#/### => #..#/..../..../#..#"
    );
}