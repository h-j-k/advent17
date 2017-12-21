package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day20.getClosestParticleToOrigin;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day20Test {

    @Test
    public void testGetClosestParticleToOrigin() {
        assertThat(getClosestParticleToOrigin(EXAMPLE), equalTo(0));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "p=<3,0,0>, v=<2,0,0>, a=<-1,0,0>",
            "p=<4,0,0>, v=<0,0,0>, a=<-2,0,0>"
    );

}