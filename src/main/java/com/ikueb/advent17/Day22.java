package com.ikueb.advent17;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
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
        Set<Node> nodes = getNodes(input);
        Direction direction = Direction.N;
        Node current = findOrAdd(nodes, 0, 0);
        int counter = 0;
        for (int i = 0; i < iterations; i++) {
            Step step = current.getNextStep(direction, header, updater);
            direction = step.direction;
            current = findOrAdd(nodes, step.x, step.y);
            if (step.hasCausedInfection) {
                counter++;
            }
        }
        return counter;
    }

    private static Node findOrAdd(Set<Node> nodes, int x, int y) {
        Optional<Node> node = nodes.stream()
                .filter(n -> n.x == x && n.y == y)
                .findFirst();
        if (node.isPresent()) {
            return node.get();
        }
        Node newNode = new Node(x, y, State.CLEAN);
        nodes.add(newNode);
        return newNode;
    }

    private static Set<Node> getNodes(List<String> input) {
        int edge = input.size() / 2;
        return IntStream.rangeClosed(-1 * edge, edge)
                .boxed()
                .flatMap(y -> IntStream.rangeClosed(-1 * edge, edge)
                        .mapToObj(x -> new Node(x, 0 - y,
                                input.get(y + edge).charAt(x + edge) == '#'
                                        ? State.INFECTED : State.CLEAN)))
                .collect(Collectors.toSet());

    }

    private static final class Step {
        final Direction direction;
        final int x;
        final int y;
        final boolean hasCausedInfection;

        Step(Direction direction, int x, int y, boolean hasCausedInfection) {
            this.direction = direction;
            this.x = direction.nextX(x);
            this.y = direction.nextY(y);
            this.hasCausedInfection = hasCausedInfection;
        }
    }

    private static final class Node {
        private final int x;
        private final int y;
        private State state;

        Node(int x, int y, State state) {
            this.x = x;
            this.y = y;
            this.state = state;
        }

        Step getNextStep(Direction direction,
                         BiFunction<State, Direction, Direction> header,
                         UnaryOperator<State> updater) {
            Direction nextDirection = header.apply(state, direction);
            state = updater.apply(state);
            return new Step(nextDirection, x, y, state == State.INFECTED);
        }
    }

    enum State {
        CLEAN, WEAKENED, INFECTED, FLAGGED;

        State next() {
            return values()[ordinal() + 1 == values().length ? 0 : ordinal() + 1];
        }
    }

    enum Direction {
        N(x -> x, y -> y + 1),
        E(x -> x + 1, y -> y),
        S(x -> x, y -> y - 1),
        W(x -> x - 1, y -> y);

        private final IntUnaryOperator x_op;
        private final IntUnaryOperator y_op;

        Direction(IntUnaryOperator x_op, IntUnaryOperator y_op) {
            this.x_op = x_op;
            this.y_op = y_op;
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

        int nextX(int x) {
            return x_op.applyAsInt(x);
        }

        int nextY(int y) {
            return y_op.applyAsInt(y);
        }
    }
}
