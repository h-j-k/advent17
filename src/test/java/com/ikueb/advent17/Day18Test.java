package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day18.recoverFrequency;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day18Test {

    @Test
    public void testRecoverFrequency() {
        assertThat(recoverFrequency(EXAMPLE), equalTo(4));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "set a 1",
            "add a 2",
            "mul a a",
            "mod a 5",
            "snd a",
            "set a 0",
            "rcv a",
            "jgz a -1",
            "set a 1",
            "jgz a -2"
    );

}