package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day21.countPixelsOnAfterIterations;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day21Test {

    @Test
    public void testCountPixelsOnAfterIterations() {
        assertThat(countPixelsOnAfterIterations(2, EXAMPLE), equalTo(12L));
        assertThat(countPixelsOnAfterIterations(5, RULES), equalTo(190L));
        assertThat(countPixelsOnAfterIterations(18, RULES), equalTo(2335049L));
    }

    @Test(expectedExceptions = UnexpectedException.class,
            expectedExceptionsMessageRegExp = "^Unable to parse: malformed_line")
    public void testUnableToParseMalformedLine() {
        Day21.Rule.from("malformed_line");
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "../.# => ##./#../...",
            ".#./..#/### => #..#/..../..../#..#"
    );

    private static final List<String> RULES = getInput(Day21.class);
}