package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day14.usedSquares;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day14Test {

    @Test
    public void testUsedSquares() {
        assertThat(usedSquares("flqrgnkx"), equalTo(8108));
    }
}