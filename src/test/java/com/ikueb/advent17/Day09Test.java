package com.ikueb.advent17;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.stream.Stream;

import static com.ikueb.advent17.Day09.process;
import static com.ikueb.advent17.Utils.getSingleInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day09Test {

    @DataProvider(name = "groupCount")
    public Iterator<Object[]> getGroupCounts() {
        return Stream.of(
                with("{}", 1),
                with("{{{}}}", 6),
                with("{{},{}}", 5),
                with("{{{},{},{{}}}}", 16),
                with("{<a>,<a>,<a>,<a>}", 1),
                with("{{<ab>},{<ab>},{<ab>},{<ab>}}", 9),
                with("{{<!!>},{<!!>},{<!!>},{<!!>}}", 9),
                with("{{<a!>},{<a!>},{<a!>},{<ab>}}", 3),
                with(INPUT, 13154)
        ).iterator();
    }

    @Test(dataProvider = "groupCount")
    public void testGroupCount(String input, int result) {
        assertThat(process(input).getGroupCount(), equalTo(result));
    }

    @DataProvider(name = "garbageCount")
    public Iterator<Object[]> getGarbageCounts() {
        return Stream.of(
                with("<>", 0),
                with("<random characters>", 17),
                with("<<<<>", 3),
                with("<{!>}>", 2),
                with("<!!>", 0),
                with("<!!!>>", 0),
                with("<{o\"i!a,<{i<a>", 10),
                with(INPUT, 6369)
        ).iterator();
    }

    @Test(dataProvider = "garbageCount")
    public void testGarbageCount(String input, int result) {
        assertThat(process(input).getGarbageCount(), equalTo(result));
    }

    private static Object[] with(String input, int result) {
        return new Object[]{input, result};
    }

    private static final String INPUT = getSingleInput(Day09.class);

}