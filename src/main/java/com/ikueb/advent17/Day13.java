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

    static final class Layer {

        private static final Pattern PARSER = Pattern.compile(
                "(?<position>[0-9]+): (?<level>[0-9]+)");

        private final int position;
        private final int level;

        private Layer(int position, int level) {
            this.position = position;
            this.level = level;
        }

        private Set<Integer> getTimesAtTop() {
            return IntStream.rangeClosed(0, position)
                    .filter(i -> level == 1 || i % (2 * (level - 1)) == 0)
                    .boxed()
                    .collect(Collectors.toSet());
        }

        int getSeverity() {
            return getTimesAtTop().contains(position) ? position * level : 0;
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
