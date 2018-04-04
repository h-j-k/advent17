package com.ikueb.advent17;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class Day21 {

    private static final Supplier<Area> AREA_SUPPLIER = () ->
            new Area(Arrays.asList(".#.", "..#", "###"));

    private Day21() {
        // empty
    }

    static long countPixelsOnAfterIterations(int iterations, List<String> input) {
        var rules = input.stream()
                .map(Rule::from)
                .collect(Collectors.toUnmodifiableList());
        return Stream.iterate(AREA_SUPPLIER.get(), a -> a.next(rules))
                .skip(iterations)
                .findFirst()
                .map(Area::countPixelsOn)
                .orElseThrow(() -> new UnexpectedException("number of pixels."));
    }

    private static final class Area {
        private final List<String> grid;
        private final int size;
        private int added = 0;

        Area(int size) {
            this.grid = new ArrayList<>(size);
            this.size = size;
        }

        Area(List<String> grid) {
            this.grid = grid;
            this.size = grid.size();
        }

        Area next(List<Rule> rules) {
            var newSize = size % 2 == 0 ? (size / 2) * 3 : (size / 3) * 4;
            return split()
                    .map(v -> v.convert(rules))
                    .reduce(new Area(newSize), Area::join);
        }

        private Stream<Area> split() {
            if (this.size == 3) {
                return Stream.of(this);
            }
            var columns = size % 2 == 0 ? size / 2 : size / 3;
            var width = size / columns;
            return IntStream.iterate(0, i -> i < size, i -> i + width)
                    .boxed()
                    .flatMap(i -> IntStream.iterate(0, j -> j < size, j -> j + width)
                            .boxed()
                            .map(j -> IntStream.range(i, i + width)
                                    .mapToObj(y -> grid.get(y).substring(j, j + width))
                                    .collect(Collectors.collectingAndThen(
                                            Collectors.toUnmodifiableList(), Area::new))));
        }

        private Area convert(List<Rule> rules) {
            return rules.stream()
                    .map(rule -> rule.getConvertedOutput(this))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElseThrow(() -> new UnexpectedException("pattern."));
        }

        private Area join(Area other) {
            var columns = size / other.size;
            if (added % columns == 0) {
                grid.addAll(other.grid);
            } else {
                var start = other.size * (added / columns);
                var iterator = other.grid.iterator();
                IntStream.iterate(start, i -> i < start + other.size, i -> i + 1)
                        .forEach(i -> grid.set(i, grid.get(i) + iterator.next()));
            }
            added++;
            return this;
        }

        long countPixelsOn() {
            return grid.stream()
                    .flatMapToInt(String::chars)
                    .filter(v -> v == '#')
                    .count();
        }

        Area flipHorizontal() {
            return with(IntStream.range(0, size)
                    .mapToObj(i -> grid.get(size - i - 1)));
        }

        Area flipVertical() {
            return with(grid.stream()
                    .map(v -> new StringBuilder(v).reverse().toString()));
        }

        Area rotate() {
            return with(IntStream.range(0, size)
                    .mapToObj(x -> IntStream.range(0, size)
                            .mapToObj(grid::get)
                            .map(v -> String.valueOf(v.charAt(x)))
                            .collect(Collectors.joining())));
        }

        private static Area with(Stream<String> stream) {
            return stream.collect(Collectors.collectingAndThen(
                    Collectors.toUnmodifiableList(), Area::new));
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Area && Objects.equals(grid, ((Area) o).grid);
        }

        @Override
        public int hashCode() {
            return Objects.hash(grid);
        }
    }

    static final class Rule {
        private static final Pattern PARSER = Pattern.compile(
                "(?<input>[.#/]+) => (?<output>[.#/]+)");
        private static final Pattern SPLITTER = Pattern.compile("/");

        private final List<Area> permutations;
        private final Supplier<Area> output;

        Rule(Area input, Supplier<Area> output) {
            this.permutations = Arrays.asList(
                    input,
                    input.flipHorizontal(),
                    input.flipVertical(),
                    input.flipHorizontal().flipVertical(),
                    input.rotate(),
                    input.rotate().flipHorizontal().flipVertical());
            this.output = output;
        }

        Optional<Area> getConvertedOutput(Area input) {
            return permutations.contains(input)
                    ? Optional.of(output.get())
                    : Optional.empty();
        }

        static Rule from(String rule) {
            var matcher = PARSER.matcher(rule);
            if (!matcher.matches()) {
                throw new UnexpectedException("rule but got: " + rule);
            }
            return new Rule(
                    convert(matcher.group("input")),
                    () -> convert(matcher.group("output")));
        }

        private static Area convert(String area) {
            return new Area(SPLITTER.splitAsStream(area)
                    .collect(Collectors.toUnmodifiableList()));
        }
    }
}
