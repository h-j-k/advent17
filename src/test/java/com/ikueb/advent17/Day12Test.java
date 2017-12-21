package com.ikueb.advent17;

import com.ikueb.advent17.Day12.Program;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.ikueb.advent17.Day12.getGroupCount;
import static com.ikueb.advent17.Day12.getTotalGroupCount;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day12Test {

    @Test
    public void testGroupCount() {
        assertThat(getGroupCount(EXAMPLE, 0), equalTo(6));
        assertThat(getGroupCount(INPUTS, 0), equalTo(141));
    }

    @Test
    public void testTotalGroupsCount() {
        assertThat(getTotalGroupCount(EXAMPLE), equalTo(2));
        assertThat(getTotalGroupCount(INPUTS), equalTo(171));
    }

    @Test(expectedExceptions = UnexpectedException.class,
            expectedExceptionsMessageRegExp = "^Unable to parse: malformed_line")
    public void testUnableToParseMalformedLine() {
        Program.parse("malformed_line");
    }

    private static final List<String> EXAMPLE = Arrays.asList(
            "0 <-> 2",
            "1 <-> 1",
            "2 <-> 0, 3, 4",
            "3 <-> 2, 4",
            "4 <-> 2, 3, 6",
            "5 <-> 6",
            "6 <-> 4, 5"
    );

    private static final List<String> INPUTS = getInput(Day12.class);
}