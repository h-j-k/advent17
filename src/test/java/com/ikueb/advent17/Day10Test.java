package com.ikueb.advent17;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ikueb.advent17.Day10.hash;
import static com.ikueb.advent17.Day10.knotAndMultiplyFirstTwo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day10Test {

    @Test
    public void testPartOne() {
        assertThat(knotAndMultiplyFirstTwo(5, new int[]{3, 4, 1, 5}), equalTo(12));
        assertThat(knotAndMultiplyFirstTwo(256, LENGTHS), equalTo(46600));
    }

    @DataProvider(name = "partTwo")
    public Iterator<Object[]> getTestCases() {
        return Stream.of(
                with("", "a2582a3a0e66e6e86e3812dcb672a272"),
                with("AoC 2017", "33efeb34ea91902bb2f59c9920caa6cd"),
                with("1,2,3", "3efbe78a8d82f29979031a4aa0b16a9d"),
                with("1,2,4", "63960835bcdc130f0b66d7ff4f6a5a8e"),
                with(Arrays.stream(LENGTHS)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(",")), "23234babdc6afa036749cfa9b597de1b")
        ).iterator();
    }

    private static Object[] with(String input, String hash) {
        return new Object[]{input, hash};
    }

    @Test(dataProvider = "partTwo")
    public void testPartTwo(String input, String hash) {
        assertThat(hash(input), equalTo(hash));
    }

    private static final int[] LENGTHS = {18, 1, 0, 161, 255, 137, 254, 252, 14, 95, 165, 33, 181, 168, 2, 188};
}