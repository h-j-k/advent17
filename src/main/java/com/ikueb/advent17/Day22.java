package com.ikueb.advent17;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Day22 {

    private Day22() {
        // empty
    }

    static int countInfectedNodes(List<String> input, int iterations,
                                  BiFunction<State, Direction, Direction> header,
                                  UnaryOperator<State> updater) {
        Map<Point, Node> nodes = getNodes(input);
        Direction direction = Direction.N;
        Node current = nodes.computeIfAbsent(new Point(0, 0), Node::new);
        int counter = 0;
        for (int i = 0; i < iterations; i++) {
            Step step = current.getNextStep(direction, header, updater);
            direction = step.direction;
            current = nodes.computeIfAbsent(step.point, Node::new);
            if (step.hasCausedInfection) {
                counter++;
            }
        }
        return counter;
    }

    private static Map<Point, Node> getNodes(List<String> input) {
        int edge = input.size() / 2;
        return IntStream.rangeClosed(-1 * edge, edge)
                .boxed()
                .flatMap(y -> IntStream.rangeClosed(-1 * edge, edge)
                        .mapToObj(x -> new Node(new Point(x, 0 - y),
                                input.get(y + edge).charAt(x + edge) == '#'
                                        ? State.INFECTED : State.CLEAN)))
                .collect(Collectors.toMap(Node::getPoint, Function.identity()));

    }

    private static final class Step {
        final Direction direction;
        final Point point;
        final boolean hasCausedInfection;

        Step(Direction direction, Point point, boolean hasCausedInfection) {
            this.direction = direction;
            this.point = direction.next(point);
            this.hasCausedInfection = hasCausedInfection;
        }
    }

    private static final class Node {
        private final Point point;
        private State state;

        Node(Point point) {
            this(point, State.CLEAN);
        }

        Node(Point point, State state) {
            this.point = point;
            this.state = state;
        }

        Point getPoint() {
            return point;
        }

        Step getNextStep(Direction direction,
                         BiFunction<State, Direction, Direction> header,
                         UnaryOperator<State> updater) {
            Direction nextDirection = header.apply(state, direction);
            state = updater.apply(state);
            return new Step(nextDirection, point, state == State.INFECTED);
        }
    }

    private static final class Point {
        private final int x;
        private final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Point
                    && x == ((Point) o).x
                    && y == ((Point) o).y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    enum State {
        CLEAN, WEAKENED, INFECTED, FLAGGED;

        private static final List<State> STATES =
                Arrays.stream(values()).collect(Collectors.toList());
        private static final int SIZE = STATES.size();

        State next() {
            return STATES.get(ordinal() + 1 == SIZE ? 0 : ordinal() + 1);
        }
    }

    enum Direction {
        N(x -> x, y -> y + 1),
        E(x -> x + 1, y -> y),
        S(x -> x, y -> y - 1),
        W(x -> x - 1, y -> y);

        private final IntUnaryOperator xOp;
        private final IntUnaryOperator yOp;

        Direction(IntUnaryOperator xOp, IntUnaryOperator yOp) {
            this.xOp = xOp;
            this.yOp = yOp;
        }

        private static final List<Direction> DIRECTIONS =
                Arrays.stream(values()).collect(Collectors.toList());
        private static final int SIZE = DIRECTIONS.size();

        Direction right() {
            return DIRECTIONS.get(ordinal() + 1 == SIZE ? 0 : ordinal() + 1);
        }

        Direction left() {
            return DIRECTIONS.get(ordinal() - 1 == -1 ? SIZE - 1 : ordinal() - 1);
        }

        Direction reverse() {
            return DIRECTIONS.get((ordinal() + 2) % SIZE);
        }

        Point next(Point point) {
            return new Point(xOp.applyAsInt(point.x), yOp.applyAsInt(point.y));
        }
    }
}
