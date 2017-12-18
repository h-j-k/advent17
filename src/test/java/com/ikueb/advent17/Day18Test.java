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
        assertThat(recoverFrequency(EXAMPLE), equalTo(4L));
        assertThat(recoverFrequency(INSTRUCTIONS), equalTo(9423L));
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

    private static final List<String> INSTRUCTIONS = Arrays.asList(
            "set i 31",
            "set a 1",
            "mul p 17",
            "jgz p p",
            "mul a 2",
            "add i -1",
            "jgz i -2",
            "add a -1",
            "set i 127",
            "set p 622",
            "mul p 8505",
            "mod p a",
            "mul p 129749",
            "add p 12345",
            "mod p a",
            "set b p",
            "mod b 10000",
            "snd b",
            "add i -1",
            "jgz i -9",
            "jgz a 3",
            "rcv b",
            "jgz b -1",
            "set f 0",
            "set i 126",
            "rcv a",
            "rcv b",
            "set p a",
            "mul p -1",
            "add p b",
            "jgz p 4",
            "snd a",
            "set a b",
            "jgz 1 3",
            "snd b",
            "set f 1",
            "add i -1",
            "jgz i -11",
            "snd a",
            "jgz f -16",
            "jgz a -19"
    );

}