package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day17.getAfter0For50Million;
import static com.ikueb.advent17.Day17.getAfter2017For2017;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day17Test {

    @Test
    public void testGetAfter2017For2017() {
        assertThat(getAfter2017For2017(3), equalTo(638));
        assertThat(getAfter2017For2017(377), equalTo(596));
    }

    @Test
    public void testGetAfter0For50Million() {
        assertThat(getAfter0For50Million(377), equalTo(39_051_595));
    }

}