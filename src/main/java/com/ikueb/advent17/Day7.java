package com.ikueb.advent17;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class Day7 {

    private Day7() {
        // empty
    }

    static String getBottomProgram(Collection<String> programs) {
        Map<String, Program> map = programs.stream()
                .map(Program::parse)
                .collect(Collectors.toMap(Program::getName, Function.identity()));
        map.values().forEach(p -> p.setChildren(map));
        return map.entrySet().stream()
                .filter(entry -> !entry.getValue().hasParent())
                .findAny()
                .map(Map.Entry::getKey)
                .orElseThrow(
                        () -> new UnexpectedOutcomeException("Should find a parent"));
    }

    private static final class Program {

        private static final Pattern LINE_PARSER =
                Pattern.compile("(?<name>[a-z]+) \\((?<weight>\\d+)\\)( -> (?<children>.+))?");
        private static final Pattern CHILDREN_PARSER =
                Pattern.compile(", ");

        private Program parent = null;
        private final String name;
        private final int weight;
        private final Set<String> childrenNames;
        private final Set<Program> children = new HashSet<>();

        Program(String name, int weight, Set<String> childrenNames) {
            this.name = name;
            this.weight = weight;
            this.childrenNames = childrenNames;
        }

        void setChildren(Map<String, Program> map) {
            childrenNames.stream().map(map::get).forEach(p -> {
                p.parent = this;
                children.add(p);
            });
        }

        String getName() {
            return name;
        }

        boolean hasParent() {
            return parent != null;
        }

        static Program parse(String line) {
            Matcher matcher = LINE_PARSER.matcher(line);
            if (!matcher.matches()) {
                throw new UnexpectedOutcomeException("Unable to parse: " + line);
            }
            return new Program(
                    matcher.group("name"),
                    Integer.parseInt(matcher.group("weight")),
                    CHILDREN_PARSER.splitAsStream(
                            Objects.toString(matcher.group("children"), ""))
                            .collect(Collectors.toSet()));
        }
    }
}
