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
        assertThat(findStrongestBridge(EXAMPLE), equalTo(31));
        assertThat(findStrongestBridge(INPUTS), equalTo(2006));
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