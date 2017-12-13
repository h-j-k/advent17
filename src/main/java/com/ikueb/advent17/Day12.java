package com.ikueb.advent17;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class Day12 {

    private Day12() {
        // empty
    }

    static int getGroupCount(Collection<String> programs, int groupId) {
        Map<Integer, Program> map = generateMap(programs);
        Program target = map.get(groupId);
        Set<Program> seen = new HashSet<>(Collections.singleton(target));
        aggregate(target.streamTargetsExcluding(seen), seen);
        return seen.size();
    }

    private static Map<Integer, Program> generateMap(Collection<String> programs) {
        Map<Integer, Program> map = programs.stream()
                .map(Program::parse)
                .collect(Collectors.toMap(Program::getId, Function.identity()));
        map.values().forEach(p -> p.setTargets(map));
        return map;
    }

    private static void aggregate(Stream<Program> stream, Set<Program> seen) {
        stream.peek(seen::add)
                .forEach(v -> aggregate(v.streamTargetsExcluding(seen), seen));
    }

    static final class Program {

        private static final Pattern LINE_PARSER = Pattern.compile(
                "(?<id>[0-9]+) <-> (?<target>.+)");
        private static final Pattern TARGET_PARSER = Pattern.compile(", ");

        private final int id;
        private final Set<Integer> targetNames;
        private final Set<Program> targets = new HashSet<>();

        Program(int id, Set<Integer> targetNames) {
            this.id = id;
            this.targetNames = targetNames;
        }

        void setTargets(Map<Integer, Program> map) {
            targetNames.stream().map(map::get).forEach(targets::add);
        }

        int getId() {
            return id;
        }

        Stream<Program> streamTargetsExcluding(Set<Program> seen) {
            return targets.stream()
                    .filter(program -> !seen.contains(program));
        }

        static Program parse(String line) {
            Matcher matcher = LINE_PARSER.matcher(line);
            if (!matcher.matches()) {
                throw new UnexpectedException("Unable to parse: " + line);
            }
            return new Program(
                    Integer.parseInt(matcher.group("id")),
                    TARGET_PARSER.splitAsStream(matcher.group("target"))
                            .map(Integer::valueOf)
                            .collect(Collectors.collectingAndThen(Collectors.toSet(),
                                    Collections::unmodifiableSet)));
        }
    }
}
