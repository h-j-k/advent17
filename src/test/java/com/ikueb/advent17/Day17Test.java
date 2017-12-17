package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day17.getNextAfter2017WithSteps;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day17Test {

    @Test
    public void testGetNextValueAfter2017() {
        assertThat(getNextAfter2017WithSteps(3), equalTo(638));
        assertThat(getNextAfter2017WithSteps(377), equalTo(596));
    }

}