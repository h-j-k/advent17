package com.ikueb.advent17;

import com.ikueb.advent17.Day13.*;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day13.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day13Test {

    @Test
    public void testTripSeverity() {
        assertThat(getTripSeverity(EXAMPLE), equalTo(24));
        assertThat(getTripSeverity(LEVELS), equalTo(1504));
    }

    @Test
    public void testMinimumDelay() {
        assertThat(getMinimumDelay(EXAMPLE), equalTo(10));
        assertThat(getMinimumDelay(LEVELS), equalTo(3823370));
    }

    @Test(expectedExceptions = UnexpectedException.class,
            expectedExceptionsMessageRegExp =
                    "Expecting a layer but got: malformed_line.")
    public void testUnableToParseMalformedLine() {
        Layer.parse("malformed_line");
    }

    @Test(expectedExceptions = UnexpectedException.class,
            expectedExceptionsMessageRegExp =
                    "Expecting a delay less than " + MAX_DELAY + ".")
    public void testExpectingADelay() {
        getMinimumDelay(Arrays.asList("0: 1", "1: 1"));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "0: 3",
            "1: 2",
            "4: 4",
            "6: 4"
    );

    private static final List<String> LEVELS = Arrays.asList(
            "0: 3",
            "1: 2",
            "2: 4",
            "4: 6",
            "6: 4",
            "8: 6",
            "10: 5",
            "12: 6",
            "14: 9",
            "16: 6",
            "18: 8",
            "20: 8",
            "22: 8",
            "24: 8",
            "26: 8",
            "28: 8",
            "30: 12",
            "32: 14",
            "34: 10",
            "36: 12",
            "38: 12",
            "40: 10",
            "42: 12",
            "44: 12",
            "46: 12",
            "48: 12",
            "50: 12",
            "52: 14",
            "54: 14",
            "56: 12",
            "62: 12",
            "64: 14",
            "66: 14",
            "68: 14",
            "70: 17",
            "72: 14",
            "74: 14",
            "76: 14",
            "82: 14",
            "86: 18",
            "88: 14",
            "96: 14",
            "98: 44"
    );
}