package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.List;

import static com.ikueb.advent17.Day04.isValid;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day04Test {

    @Test
    public void testIsValid() {
        assertThat(INPUT.stream().filter(Day04::isValid).count(), equalTo(466L));
    }

    @Test
    public void testIsValidAdvanced() {
        assertThat(INPUT.stream().filter(v -> isValid(v, Day04::reform)).count(), equalTo(251L));
    }

    private static final List<String> INPUT = getInput(Day04.class);
}