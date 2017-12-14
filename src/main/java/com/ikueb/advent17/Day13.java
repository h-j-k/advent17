package com.ikueb.advent17;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Day13 {

    static final int MAX_DELAY = 10_000_000;

    private Day13() {
        // empty
    }

    static int getTripSeverity(List<String> levels) {
        return levels.stream()
                .map(Layer::parse)
                .mapToInt(Layer::getSeverityPlusDelay)
                .sum();
    }

    static int getMinimumDelay(List<String> levels) {
        List<Layer> layers = levels.stream()
                .map(Layer::parse)
                .collect(Collectors.toList());
        return IntStream.range(1, MAX_DELAY)
                .filter(i -> layers.stream().allMatch(v -> v.getSeverityPlusDelay(i) == 0))
                .findFirst()
                .orElseThrow(() -> new UnexpectedException(
                        "Expecting a delay less than " + MAX_DELAY));
    }

    static final class Layer {

        private static final Pattern PARSER = Pattern.compile(
                "(?<position>[0-9]+): (?<level>[0-9]+)");

        private final int position;
        private final int level;

        private Layer(int position, int level) {
            this.position = position;
            this.level = level;
        }

        int getSeverityPlusDelay() {
            return getSeverityPlusDelay(0);
        }

        int getSeverityPlusDelay(int delay) {
            return level == 1 || (delay + position) % (2 * (level - 1)) == 0
                    ? delay + position * level : 0;
        }

        static Layer parse(String layer) {
            Matcher matcher = PARSER.matcher(layer);
            if (!matcher.matches()) {
                throw new UnexpectedException("Unable to parse: " + layer);
            }
            return new Layer(Integer.parseInt(matcher.group("position")),
                    Integer.parseInt(matcher.group("level")));
        }
    }
}
