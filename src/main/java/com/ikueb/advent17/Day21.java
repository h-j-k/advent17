package com.ikueb.advent17;

import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Matcher;
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
        List<Rule> rules = input.stream().map(Rule::from).collect(Collectors.toList());
        return Stream.iterate(AREA_SUPPLIER.get(), a -> a.next(rules))
                .skip(iterations)
                .findFirst()
                .map(Area::countPixelsOn)
                .orElseThrow(() -> new UnexpectedException("Expecting pixels on."));
    }

    private static final class Area {
        private final List<String> area;
        private final int size;
        private int added = 0;

        Area(int size) {
            this.area = new ArrayList<>();
            this.size = size;
        }

        Area(List<String> area) {
            this.area = new ArrayList<>(area);
            this.size = area.size();
        }

        Area next(List<Rule> rules) {
            int newSize = size % 2 == 0 ? (size / 2) * 3 : (size / 3) * 4;
            return split()
                    .map(area -> area.convert(rules))
                    .reduce(new Area(newSize), Area::join);
        }

        private Stream<Area> split() {
            if (this.size == 3) {
                return Stream.of(this);
            }
            int columns = size % 2 == 0 ? size / 2 : size / 3;
            int width = size / columns;
            List<Area> result = new ArrayList<>();
            for (int i = 0; i < size; i += width) {
                for (int j = 0; j < size; j += width) {
                    int x = j;
                    result.add(new Area(IntStream.range(i, i + width)
                            .mapToObj(y -> area.get(y).substring(x, x + width))
                            .collect(Collectors.toList())));
                }
            }
            return result.stream();
        }

        private Area convert(List<Rule> rules) {
            return rules.stream()
                    .map(rule -> rule.getConvertedOutput(this))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .findFirst()
                    .orElseThrow(() -> new UnexpectedException(
                            "Expecting an output."));
        }

        private Area join(Area other) {
            int columns = size / other.size;
            if (added % columns == 0) {
                area.addAll(other.area);
            } else {
                int start = other.size * (added / columns);
                Iterator<String> iterator = other.area.iterator();
                for (int i = start; i < start + other.size; i++) {
                    area.set(i, area.get(i) + iterator.next());
                }
            }
            added++;
            return this;
        }

        long countPixelsOn() {
            return area.stream()
                    .flatMapToInt(String::chars)
                    .filter(v -> v == '#')
                    .count();
        }

        Area flipHorizontal() {
            return with(IntStream.range(0, size)
                    .mapToObj(i -> area.get(size - i - 1)));
        }

        Area flipVertical() {
            return with(area.stream()
                    .map(v -> new StringBuilder(v).reverse().toString()));
        }

        Area rotate() {
            return with(IntStream.range(0, size)
                    .mapToObj(x -> IntStream.range(0, size)
                            .mapToObj(area::get)
                            .map(v -> String.valueOf(v.charAt(x)))
                            .collect(Collectors.joining())));
        }

        private static Area with(Stream<String> stream) {
            return stream.collect(Collectors.collectingAndThen(
                    Collectors.toList(), Area::new));
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Area && Objects.equals(area, ((Area) o).area);
        }

        @Override
        public int hashCode() {
            return Objects.hash(area);
        }
    }

    static final class Rule {
        private static final Pattern PARSER = Pattern.compile(
                "(?<input>[.#/]+) => (?<output>[.#/]+)");
        private static final Pattern SPLITTER = Pattern.compile("/");

        private final Area input;
        private final Supplier<Area> output;

        Rule(Area input, Supplier<Area> output) {
            this.input = input;
            this.output = output;
        }

        Optional<Area> getConvertedOutput(Area input) {
            return getInputPermutations().anyMatch(input::equals)
                    ? Optional.of(output.get())
                    : Optional.empty();
        }

        Stream<Area> getInputPermutations() {
            return Stream.of(
                    input,
                    input.flipHorizontal(),
                    input.flipVertical(),
                    input.flipHorizontal().flipVertical(),
                    input.rotate(),
                    input.rotate().flipHorizontal().flipVertical());
        }

        static Rule from(String rule) {
            Matcher matcher = PARSER.matcher(rule);
            if (!matcher.matches()) {
                throw new UnexpectedException("Unable to parse: " + rule);
            }
            return new Rule(
                    convert(matcher.group("input")), () -> convert(matcher.group("output")));
        }

        private static Area convert(String area) {
            return new Area(SPLITTER.splitAsStream(area).collect(Collectors.toList()));
        }
    }
}
