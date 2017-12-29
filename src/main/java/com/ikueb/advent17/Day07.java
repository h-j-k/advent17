package com.ikueb.advent17;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

final class Day07 {

    private Day07() {
        // empty
    }

    static String getBottomProgram(Collection<String> programs) {
        return getBottomProgram(generateMap(programs));
    }

    private static String getBottomProgram(Map<String, Program> map) {
        return map.entrySet().stream()
                .filter(entry -> !entry.getValue().hasParent())
                .map(Entry::getKey)
                .iterator()
                .next();
    }

    static int getCorrectedWeight(Collection<String> programs) {
        Map<String, Program> map = generateMap(programs);
        return getCorrectedWeight(0, map.get(getBottomProgram(map)));
    }

    private static int getCorrectedWeight(int delta, Program parent) {
        Map<Integer, Set<Program>> childrenWeight =
                parent.getChildrenWeight().entrySet().stream()
                        .collect(Collectors.groupingBy(
                                Entry::getValue,
                                Collectors.mapping(Entry::getKey, Collectors.toSet())));
        if (childrenWeight.size() < 2) {
            return parent.getWeight() - delta;
        }
        IntSummaryStatistics stats = childrenWeight.keySet().stream()
                .collect(Collectors.summarizingInt(Integer::intValue));
        Entry<Integer, Set<Program>> unbalanced = childrenWeight.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .iterator()
                .next();
        int newDelta = unbalanced.getKey() == stats.getMin()
                ? stats.getMin() - stats.getMax()
                : stats.getMax() - stats.getMin();
        return getCorrectedWeight(newDelta, unbalanced.getValue().iterator().next());
    }

    private static Map<String, Program> generateMap(Collection<String> programs) {
        Map<String, Program> map = programs.stream()
                .map(Program::parse)
                .collect(MainUtils.mapWithKey(Program::getName));
        map.values().forEach(p -> p.setChildren(map));
        return map;
    }

    static final class Program {

        private static final Pattern LINE_PARSER = Pattern.compile(
                "(?<name>[a-z]+) \\((?<weight>\\d+)\\)( -> (?<children>.+))?");
        private static final Pattern CHILDREN_PARSER = Pattern.compile(", ");

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

        int getWeight() {
            return weight;
        }

        int getTotalWeight() {
            return weight + children.stream()
                    .mapToInt(Program::getTotalWeight)
                    .sum();
        }

        Map<Program, Integer> getChildrenWeight() {
            return children.stream()
                    .collect(MainUtils.mapWithValue(Program::getTotalWeight));
        }

        static Program parse(String line) {
            Matcher matcher = LINE_PARSER.matcher(line);
            if (!matcher.matches()) {
                throw new UnexpectedException("program but got: " + line);
            }
            return new Program(
                    matcher.group("name"),
                    Integer.parseInt(matcher.group("weight")),
                    CHILDREN_PARSER.splitAsStream(
                            Objects.toString(matcher.group("children"), ""))
                            .filter(v -> !v.isEmpty())
                            .collect(MainUtils.toUnmodifiableSet()));
        }
    }
}
