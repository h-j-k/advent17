package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day24.findStrongestBridge;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day24Test {

    @Test
    public void testFindStrongestBridge() {
        assertThat(findStrongestBridge(
                EXAMPLE, Day24.Result.BY_WEIGHT).getResult(), equalTo(31));
        assertThat(findStrongestBridge(
                INPUTS, Day24.Result.BY_WEIGHT).getResult(), equalTo(2006));
    }

    @Test
    public void testFindLongestAndStrongestBridge() {
        assertThat(findStrongestBridge(
                EXAMPLE, Day24.Result.BY_LENGTH_THEN_WEIGHT).getResult(), equalTo(19));
        assertThat(findStrongestBridge(
                INPUTS, Day24.Result.BY_LENGTH_THEN_WEIGHT).getResult(), equalTo(1994));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "0/2",
            "2/2",
            "2/3",
            "3/4",
            "3/5",
            "0/1",
            "10/1",
            "9/10"
    );

    private static final List<String> INPUTS = getInput(Day24.class);
}