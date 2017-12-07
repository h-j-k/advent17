package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day6.getFirstDuplicateCount;
import static com.ikueb.advent17.Day6.getSecondDuplicateCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day6Test {

    @Test
    public void testFirstDuplicateCount() {
        assertThat(getFirstDuplicateCount(EXAMPLE), equalTo(5));
        assertThat(getFirstDuplicateCount(INPUT), equalTo(5042));
    }

    @Test
    public void testSecondDuplicateCount() {
        assertThat(getSecondDuplicateCount(EXAMPLE), equalTo(4));
        assertThat(getSecondDuplicateCount(INPUT), equalTo(1086));
    }

    private static final int[] EXAMPLE = {0, 2, 7, 0};
    private static final int[] INPUT = {5, 1, 10, 0, 1, 7, 13, 14, 3, 12, 8, 10, 7, 12, 0, 6};

}