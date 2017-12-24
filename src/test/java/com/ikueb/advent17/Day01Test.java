package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day01.compute;
import static com.ikueb.advent17.Utils.getSingleInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day01Test {

    @Test
    public void testSum() {
        assertThat(compute(VALUE, Day01::sum), equalTo(1047));
    }

    @Test
    public void testJumpSum() {
        assertThat(compute(VALUE, Day01::jumpSum), equalTo(982));
    }

    private static final String VALUE = getSingleInput(Day01.class);
}
