package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day10.knotAndMultiplyFirstTwo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day10Test {

    @Test
    public void testExample() {
        assertThat(knotAndMultiplyFirstTwo(5,
                new int[]{3, 4, 1, 5}), equalTo(12));
    }

}