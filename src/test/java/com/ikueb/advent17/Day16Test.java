package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day16.dance;
import static com.ikueb.advent17.Day16.danceTillDrop;
import static com.ikueb.advent17.Utils.getSingleInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day16Test {

    @Test
    public void testDance() {
        assertThat(dance("abcde", "s1,x3/4,pe/b"), equalTo("baedc"));
        assertThat(dance("abcdefghijklmnop", INSTRUCTIONS),
                equalTo("lgpkniodmjacfbeh"));
    }

    @Test
    public void testDanceTillDrop() {
        assertThat(danceTillDrop("abcdefghijklmnop", INSTRUCTIONS),
                equalTo("hklecbpnjigoafmd"));
    }

    @Test(expectedExceptions = UnexpectedException.class,
            expectedExceptionsMessageRegExp = "^Should be one of: sX, xA/B, pA/B$")
    public void testInvalidInstructions() {
        dance("", " ");
    }

    private static final String INSTRUCTIONS = getSingleInput(Day16.class);
}