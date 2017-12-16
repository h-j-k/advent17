package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day15.getMatchingLowest16Digits;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day15Test {

    @Test
    public void testPartOne() {
        assertThat(getMatchingLowest16Digits(65, 8921,
                Day15.GeneratorPair::next, 40_000_000), equalTo(588L));
        assertThat(getMatchingLowest16Digits(699, 124,
                Day15.GeneratorPair::next, 40_000_000), equalTo(600L));
    }

    @Test
    public void testPartTwo() {
        assertThat(getMatchingLowest16Digits(65, 8921,
                v -> v.next(a -> a % 4 == 0, b -> b % 8 == 0), 5_000_000), equalTo(309L));
        assertThat(getMatchingLowest16Digits(699, 124,
                v -> v.next(a -> a % 4 == 0, b -> b % 8 == 0), 5_000_000), equalTo(313L));
    }
}