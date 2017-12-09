package com.ikueb.advent17;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.stream.Stream;

import static com.ikueb.advent17.Day9.getGroupCount;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day9Test {

    @DataProvider(name = "examples")
    public Iterator<Object[]> getExamples() {
        return Stream.of(
                with("{}", 1),
                with("{{{}}}", 6),
                with("{{},{}}", 5),
                with("{{{},{},{{}}}}", 16),
                with("{<a>,<a>,<a>,<a>}", 1),
                with("{{<ab>},{<ab>},{<ab>},{<ab>}}", 9),
                with("{{<!!>},{<!!>},{<!!>},{<!!>}}", 9),
                with("{{<a!>},{<a!>},{<a!>},{<ab>}}", 3)
        ).iterator();
    }

    private static Object[] with(String input, int result) {
        return new Object[]{input, result};
    }

    @Test(dataProvider = "examples")
    public void testExamples(String input, int result) {
        assertThat(getGroupCount(input), equalTo(result));
    }

}