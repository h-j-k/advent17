package com.ikueb.advent17;

import java.util.regex.Pattern;
import java.util.stream.IntStream;

final class Day11 {

    private static final Pattern SPLITTER = Pattern.compile(",");

    private Day11() {
        // empty
    }

    static int getFewestStepsTo(String steps) {
        return traceSteps(steps).countStepsFromOrigin();
    }

    static int getFurthest(String steps) {
        return traceSteps(steps).getFurthest();
    }

    private static Counter traceSteps(String steps) {
        return SPLITTER.splitAsStream(steps)
                .map(step -> Cardinal.valueOf(step.toUpperCase()))
                .reduce(new Counter(), Counter::moveTo,
                        (a, b) -> { throw new UnsupportedOperationException(); });
    }

    private static final class Counter {

        private int x = 0;
        private int y = 0;
        private int z = 0;
        private int furthest = 0;

        Counter moveTo(Cardinal cardinal) {
            x += cardinal.x;
            y += cardinal.y;
            z += cardinal.z;
            furthest = Math.max(furthest, countStepsFromOrigin());
            return this;
        }

        int countStepsFromOrigin() {
            return IntStream.of(x, y, z)
                    .map(Math::abs)
                    .max()
                    .orElse(0);
        }

        int getFurthest() {
            return furthest;
        }
    }

    private enum Cardinal {
        N(0, 1, -1),
        NE(1, 0, -1),
        SE(1, -1, 0),
        S(0, -1, 1),
        SW(-1, 0, 1),
        NW(-1, 1, 0);

        private final int x;
        private final int y;
        private final int z;

        Cardinal(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
