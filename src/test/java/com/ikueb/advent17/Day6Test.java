package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day6.getUniqueRedistribution;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day6Test {

    @Test
    public void testExample() {
        assertThat(getUniqueRedistribution(0, 2, 7, 0), equalTo(5));
    }

    @Test
    public void testPartOne() {
        assertThat(getUniqueRedistribution(5, 1, 10, 0, 1, 7, 13, 14, 3, 12, 8, 10, 7, 12, 0, 6), equalTo(5042));
    }

}