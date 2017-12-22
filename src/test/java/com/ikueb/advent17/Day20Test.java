package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day20.countUncollidedParticles;
import static com.ikueb.advent17.Day20.getClosestParticleToOrigin;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day20Test {

    @Test
    public void testGetClosestParticleToOrigin() {
        assertThat(getClosestParticleToOrigin(PART_ONE), equalTo(0));
        assertThat(getClosestParticleToOrigin(INPUT), equalTo(364));
    }

    @Test
    public void testCountUncollidedParticles() {
        assertThat(countUncollidedParticles(PART_TWO), equalTo(0));
        assertThat(countUncollidedParticles(INPUT), equalTo(364));
    }

    private static final List<String> PART_ONE = Arrays.asList(
            "p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>",
            "p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>"
    );

    private static final List<String> PART_TWO = Arrays.asList(
            "p=<-6,0,0>, v=< 3,0,0>, a=< 0,0,0>",
            "p=<-4,0,0>, v=< 2,0,0>, a=< 0,0,0>",
            "p=<-2,0,0>, v=< 1,0,0>, a=< 0,0,0>",
            "p=< 3,0,0>, v=<-1,0,0>, a=< 0,0,0>"
    );

    private static final List<String> INPUT = getInput(Day20.class);
}