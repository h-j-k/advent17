package com.ikueb.advent17;

import com.ikueb.advent17.Day13.Layer;
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
        assertThat(getTripSeverity(LEVELS), equalTo(1504));
    }

    @Test(expectedExceptions = UnexpectedException.class,
            expectedExceptionsMessageRegExp = "^Unable to parse: malformed_line")
    public void testUnableToParseMalformedLine() {
        Layer.parse("malformed_line");
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