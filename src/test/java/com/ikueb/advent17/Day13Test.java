package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day13.getTripSeverity;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day13Test {

    @Test
    public void testTripSeverity() {
        assertThat(getTripSeverity(EXAMPLE), equalTo(24));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "0: 3",
            "1: 2",
            "4: 4",
            "6: 4"
    );
}