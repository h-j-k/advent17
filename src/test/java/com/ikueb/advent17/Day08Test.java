package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ikueb.advent17.Day08.getLargestRegister;
import static com.ikueb.advent17.Day08.getLargestRegisterEver;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day08Test {

    @Test
    public void testGetLargestRegister() {
        assertThat(getLargestRegister(EXAMPLE), equalTo(1));
        assertThat(getLargestRegister(INSTRUCTIONS), equalTo(5143));
    }

    @Test
    public void testGetLargestRegisterEver() {
        assertThat(getLargestRegisterEver(EXAMPLE), equalTo(10));
        assertThat(getLargestRegisterEver(INSTRUCTIONS), equalTo(6209));
    }

    @Test(expectedExceptions = UnexpectedException.class,
            expectedExceptionsMessageRegExp = "Expecting a result.")
    public void testGetLargestRegisterWithEmptyInputThrows() {
        getLargestRegister(Collections.emptyList());
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "b inc 5 if a > 1",
            "a inc 1 if b < 5",
            "c dec -10 if a >= 1",
            "c inc -20 if c == 10"
    );

    private static final List<String> INSTRUCTIONS = getInput(Day08.class);
}