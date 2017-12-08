package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day8.getLargestRegister;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day8Test {

    @Test
    public void testGetLargestRegister() {
        assertThat(getLargestRegister(EXAMPLE), equalTo(1));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "b inc 5 if a > 1",
            "a inc 1 if b < 5",
            "c dec -10 if a >= 1",
            "c inc -20 if c == 10"
    );
}