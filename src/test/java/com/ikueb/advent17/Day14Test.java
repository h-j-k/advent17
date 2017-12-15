package com.ikueb.advent17;

import org.testng.annotations.Test;

import static com.ikueb.advent17.Day14.contiguousRegions;
import static com.ikueb.advent17.Day14.usedSquares;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day14Test {

    @Test
    public void testUsedSquares() {
        assertThat(usedSquares("flqrgnkx"), equalTo(8108));
        assertThat(usedSquares("ugkiagan"), equalTo(8292));
    }

    @Test
    public void testContiguousRegions() {
        assertThat(contiguousRegions("flqrgnkx"), equalTo(1242));
        assertThat(contiguousRegions("ugkiagan"), equalTo(1069));
    }
}