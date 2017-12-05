package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day3.getFirstAfter;
import static com.ikueb.advent17.Day3.stepsTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day3Test {

    private static final int INPUT = 312051;

    @Test
    public void testStepsTo() {
        assertThat(stepsTo(INPUT), equalTo(430));
    }

    @Test
    public void testGetFirstAfter() {
        assertThat(getFirstAfter(INPUT), equalTo(312453));
    }
}