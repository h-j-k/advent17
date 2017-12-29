package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.List;

import static com.ikueb.advent17.Day02.checksumInput;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day02Test {

    @Test
    public void testChecksumForDiff() {
        assertThat(checksumInput(MATRIX, Day02::diff), equalTo(58975));
    }

    @Test
    public void testChecksumForEvenlyDivisible() {
        assertThat(checksumInput(MATRIX, Day02::evenlyDivisible), equalTo(308));
    }

    @Test(expectedExceptions = UnexpectedException.class)
    public void testUnexpectedNonEvenlyDivisibleList() {
        checksumInput(List.of("2 3"), Day02::evenlyDivisible);
    }

    private static final List<String> MATRIX = getInput(Day02.class);
}