package com.ikueb.advent17;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import static com.ikueb.advent17.Day22.State.CLEAN;
import static com.ikueb.advent17.Day22.State.INFECTED;
import static com.ikueb.advent17.Day22.countInfectedNodes;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day22Test {

    private static final BiFunction<Day22.State, Day22.Direction, Day22.Direction>
            PART_ONE_HEADER = (state, direction) ->
            state == INFECTED ? direction.right() : direction.left();
    private static final UnaryOperator<Day22.State>
            PART_ONE_UPDATER = state -> state == INFECTED ? CLEAN : INFECTED;
    private static final BiFunction<Day22.State, Day22.Direction, Day22.Direction>
            PART_TWO_HEADER = (state, direction) -> {
        switch (state) {
            case CLEAN:
                return direction.left();
            case WEAKENED:
                return direction;
            case INFECTED:
                return direction.right();
            case FLAGGED:
                return direction.reverse();
            default:
                throw new UnexpectedException("Expecting a direction.");
        }
    };
    private static final UnaryOperator<Day22.State>
            PART_TWO_UPDATER = Day22.State::next;

    @Test
    public void testPartOne() {
        assertThat(countInfectedNodes(EXAMPLE, 7,
                PART_ONE_HEADER, PART_ONE_UPDATER), equalTo(5));
        assertThat(countInfectedNodes(EXAMPLE, 70,
                PART_ONE_HEADER, PART_ONE_UPDATER), equalTo(41));
        assertThat(countInfectedNodes(EXAMPLE, 10_000,
                PART_ONE_HEADER, PART_ONE_UPDATER), equalTo(5587));
        assertThat(countInfectedNodes(INPUT, 10_000,
                PART_ONE_HEADER, PART_ONE_UPDATER), equalTo(5462));
    }

    @Test
    public void testPartTwo() {
        assertThat(countInfectedNodes(EXAMPLE, 100,
                PART_TWO_HEADER, PART_TWO_UPDATER), equalTo(26));
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "..#",
            "#..",
            "..."
    );

    private static final List<String> INPUT = getInput(Day22.class);
}