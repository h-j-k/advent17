package com.ikueb.advent17;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Day13 {

    private Day13() {
        // empty
    }

    static int getTripSeverity(List<String> levels) {
        return levels.stream()
                .map(Layer::parse)
                .mapToInt(Layer::getSeverity)
                .sum();
    }

    static int getMinimumDelay(List<String> levels) {
        return -1;
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

        int getSeverity() {
            return getSeverity(0);
        }

        int getSeverity(int delay) {
            return level == 1 || (delay + position) % (2 * (level - 1)) == 0
                    ? position * level : 0;
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
