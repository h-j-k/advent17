package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day15.getMatchingLowest16Digits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day15Test {

    @Test
    public void testMatchingLowest16Digits() {
        assertThat(getMatchingLowest16Digits(65, 8921), equalTo(588L));
        assertThat(getMatchingLowest16Digits(699, 124), equalTo(600L));
    }

}