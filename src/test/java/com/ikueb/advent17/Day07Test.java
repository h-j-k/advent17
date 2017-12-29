package com.ikueb.advent17;

import com.ikueb.advent17.Day07.Program;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.ikueb.advent17.Day07.getBottomProgram;
import static com.ikueb.advent17.Day07.getCorrectedWeight;
import static com.ikueb.advent17.Utils.getInput;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Day07Test {

    @Test
    public void testBottomProgram() {
        assertThat(getBottomProgram(EXAMPLE), equalTo("tknk"));
        assertThat(getBottomProgram(INPUTS), equalTo("ahnofa"));
    }

    @Test
    public void testCorrectedWeight() {
        assertThat(getCorrectedWeight(EXAMPLE), equalTo(60));
        assertThat(getCorrectedWeight(INPUTS), equalTo(802));
    }

    @Test
    public void testCorrectedWeightForLighterProgramAndChildren() {
        assertThat(getCorrectedWeight(LIGHTER_PROGRAM), equalTo(60));
        assertThat(getCorrectedWeight(CHILDREN), equalTo(61));
    }

    @Test
    public void testLineParsing() {
        Program parent = Program.parse("parent (2) -> child");
        assertProgram(parent, "parent", 2, 2, false, Collections.emptyMap());
        Program child = Program.parse("child (1)");
        assertProgram(child, "child", 1, 1, false, Collections.emptyMap());
        Map<String, Program> map = Stream.of(parent, child)
                .collect(MainUtils.mapWithKey(Program::getName));
        parent.setChildren(map);
        assertProgram(parent, "parent", 2, 3, false, Map.of(child, 1));
        assertProgram(child, "child", 1, 1, true, Collections.emptyMap());
        child.setChildren(map);
        assertProgram(child, "child", 1, 1, true, Collections.emptyMap());

    }

    private static void assertProgram(Program program,
                                      String name,
                                      int weight,
                                      int totalWeight,
                                      boolean hasParent,
                                      Map<Program, Integer> childrenWeight) {
        assertThat(program.getName(), equalTo(name));
        assertThat(program.getWeight(), equalTo(weight));
        assertThat(program.getTotalWeight(), equalTo(totalWeight));
        assertThat(program.hasParent(), equalTo(hasParent));
        assertThat(program.getChildrenWeight(), equalTo(childrenWeight));
    }

    @Test(expectedExceptions = UnexpectedException.class,
            expectedExceptionsMessageRegExp =
                    "Expecting a program but got: malformed_line.")
    public void testUnableToParseMalformedLine() {
        Program.parse("malformed_line");
    }

    private static final List<String> LIGHTER_PROGRAM = Arrays.asList(
            "pbga (66)",
            "xhth (57)",
            "ebii (61)",
            "havc (66)",
            "ktlj (57)",
            "fwft (72) -> ktlj, cntj, xhth",
            "qoyq (66)",
            "padx (45) -> pbga, havc, qoyq",
            "tknk (41) -> ugml, padx, fwft",
            "jptl (61)",
            "ugml (52) -> gyxo, ebii, jptl",
            "gyxo (61)",
            "cntj (57)"
    );

    private static final List<String> CHILDREN = Arrays.asList(
            "pbga (66)",
            "xhth (57)",
            "ebii (69)",
            "havc (66)",
            "ktlj (57)",
            "fwft (72) -> ktlj, cntj, xhth",
            "qoyq (66)",
            "padx (45) -> pbga, havc, qoyq",
            "tknk (41) -> ugml, padx, fwft",
            "jptl (61)",
            "ugml (60) -> gyxo, ebii, jptl",
            "gyxo (61)",
            "cntj (57)"
    );

    private static final List<String> EXAMPLE = Arrays.asList(
            "pbga (66)",
            "xhth (57)",
            "ebii (61)",
            "havc (66)",
            "ktlj (57)",
            "fwft (72) -> ktlj, cntj, xhth",
            "qoyq (66)",
            "padx (45) -> pbga, havc, qoyq",
            "tknk (41) -> ugml, padx, fwft",
            "jptl (61)",
            "ugml (68) -> gyxo, ebii, jptl",
            "gyxo (61)",
            "cntj (57)"
    );

    private static final List<String> INPUTS = getInput(Day07.class);

}