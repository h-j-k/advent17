package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day16.dance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day16Test {

    @Test
    public void testDance() {
        assertThat(dance("abcde", "s1,x3/4,pe/b"), equalTo("baedc"));
    }

}