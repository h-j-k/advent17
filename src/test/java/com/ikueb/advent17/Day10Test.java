package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day10.knotAndMultiplyFirstTwo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day10Test {

    @Test
    public void testPartOne() {
        assertThat(knotAndMultiplyFirstTwo(5, new int[]{3, 4, 1, 5}), equalTo(12));
        assertThat(knotAndMultiplyFirstTwo(256, LENGTHS), equalTo(46600));
    }

    private static final int[] LENGTHS = {18, 1, 0, 161, 255, 137, 254, 252, 14, 95, 165, 33, 181, 168, 2, 188};
}