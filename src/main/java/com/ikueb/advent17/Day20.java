package com.ikueb.advent17;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Day20 {

    private static final int ITERATIONS = 1_000;

    private Day20() {
        // empty
    }

    static int getClosestParticleToOrigin(List<String> input) {
        List<Particle> particles = toParticles(input);
        return IntStream.range(0, particles.size())
                .boxed()
                .sorted(Comparator.comparing(
                        i -> particles.get(i).getTotalAcceleration()))
                .findFirst()
                .orElseThrow(() -> new UnexpectedException("Expecting a particle."));
    }

    static int countUncollidedParticles(List<String> input) {
        List<Particle> particles = toParticles(input);
        int lastCount = -1;
        for (int i = 1; lastCount != particles.size() || i <= ITERATIONS; i++) {
            lastCount = particles.size();
            int t = i;
            particles.stream()
                    .collect(Collectors.groupingBy(p -> p.positionAt(t)))
                    .values()
                    .stream()
                    .filter(v -> v.size() > 1)
                    .forEach(particles::removeAll);
        }
        return particles.size();
    }

    private static List<Particle> toParticles(List<String> input) {
        return input.stream()
                .map(Particle::from)
                .collect(Collectors.toList());
    }

    static final class Position {
        private final int x;
        private final int y;
        private final int z;

        Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Position
                    && x == ((Position) o).x
                    && y == ((Position) o).y
                    && z == ((Position) o).z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    static final class Particle {

        private static final Pattern PARSER = Pattern.compile(
                "p=<(?<p>[,0-9-]+)>, v=<(?<v>[,0-9-]+)>, a=<(?<a>[,0-9-]+)>");
        private static final Pattern SPLITTER = Pattern.compile(",");
        private final int[] accelerations;
        private final int[] xPva;
        private final int[] yPva;
        private final int[] zPva;

        Particle(int[] positions, int[] velocities, int[] accelerations) {
            this.accelerations = accelerations;
            this.xPva = new int[]{positions[0], velocities[0], accelerations[0]};
            this.yPva = new int[]{positions[1], velocities[1], accelerations[1]};
            this.zPva = new int[]{positions[2], velocities[2], accelerations[2]};
        }

        double getTotalAcceleration() {
            return Math.sqrt(Arrays.stream(accelerations).map(i -> i * i).sum());
        }

        Position positionAt(int t) {
            return new Position(
                    positionAt(xPva, t),
                    positionAt(yPva, t),
                    positionAt(zPva, t));
        }

        private static int positionAt(int[] pva, int t) {
            return pva[0] + pva[1] * t + pva[2] * t * (t + 1) / 2;
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

        private static int[] toArray(String input) {
            return SPLITTER.splitAsStream(input).mapToInt(Integer::parseInt).toArray();
        }
    }
}
