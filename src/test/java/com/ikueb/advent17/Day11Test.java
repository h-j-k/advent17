package com.ikueb.advent17;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.stream.Stream;

import static com.ikueb.advent17.Day11.getFewestStepsTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day11Test {

    @DataProvider(name = "partOne")
    public Iterator<Object[]> getPartOneTestCases() {
        return Stream.of(
                with("ne,ne,ne", 3),
                with("ne,ne,sw,sw", 0),
                with("ne,ne,s,s", 2),
                with("se,sw,se,sw,sw", 3)
        ).iterator();
    }

    private static Object[] with(String steps, int expected) {
        return new Object[]{steps, expected};
    }

    @Test(dataProvider = "partOne")
    public void testPartOne(String steps, int expected) {
        assertThat(getFewestStepsTo(steps), equalTo(expected));
    }
}