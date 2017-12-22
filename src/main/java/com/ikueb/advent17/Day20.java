package com.ikueb.advent17;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Day20 {

    private Day20() {
        // empty
    }

    static int getClosestParticleToOrigin(List<String> input) {
        List<Particle> particles = input.stream()
                .map(Particle::from)
                .collect(Collectors.toList());
        return IntStream.range(0, particles.size())
                .boxed()
                .sorted(Comparator.comparing(i -> particles.get(i).getTotalAcceleration()))
                .findFirst()
                .orElseThrow(() -> new UnexpectedException("Expecting a particle."));
    }

    static int countUncollidedParticles(List<String> input) {
        return -1;
    }

    static final class Particle {

        private static final Pattern PARSER = Pattern.compile(
                "p=<(?<p>[,0-9-]+)>, v=<(?<v>[,0-9-]+)>, a=<(?<a>[,0-9-]+)>");
        private static final Pattern SPLITTER = Pattern.compile(",");
        private final int[] positions;
        private final int[] velocities;
        private final int[] accelerations;

        public Particle(int[] positions,
                        int[] velocities,
                        int[] accelerations) {
            this.positions = positions;
            this.velocities = velocities;
            this.accelerations = accelerations;
        }

        private double getTotalAcceleration() {
            return Math.sqrt(Arrays.stream(accelerations).map(i -> i * i).sum());
        }

        @Override
        public String toString() {
            return String.format("p=<%s>, v=<%s>, a=<%s>",
                    Arrays.toString(positions),
                    Arrays.toString(velocities),
                    Arrays.toString(accelerations));
        }

        private static int[] toArray(String input) {
            return SPLITTER.splitAsStream(input).mapToInt(Integer::parseInt).toArray();
        }

        static Particle from(String definition) {
            Matcher matcher = PARSER.matcher(definition);
            if (!matcher.matches()) {
                throw new UnexpectedException("Unable to parse: " + definition);
            }
            return new Particle(
                    toArray(matcher.group("p")),
                    toArray(matcher.group("v")),
                    toArray(matcher.group("a")));
        }
    }
}
