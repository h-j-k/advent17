package com.ikueb.advent17;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Day19 {

    private Day19() {
        // empty
    }

    static Result getPath(List<String> grid) {
        var result = new StringBuilder();
        var paths = toPaths(grid);
        var origin = paths.stream()
                .filter(Element::isStart)
                .findAny()
                .orElseThrow(() -> new UnexpectedException("starting element."));
        var direction = Direction.S;
        var counter = 1;
        for (Element previous = null, current = origin; ;
             previous = current, current = current.next(direction, paths)) {
            if (current.isLetter()) {
                result.append(current.getLetter());
            } else if (current.isCorner()) {
                direction = current.nextDirection(previous, paths);
            }
            if (current.isEnd(paths)) {
                break;
            }
            counter++;
        }
        return new Result(result.toString(), counter);
    }

    private static Set<Element> toPaths(List<String> grid) {
        return IntStream.range(0, grid.size())
                .boxed()
                .flatMap(y -> IntStream.range(0, grid.get(y).length())
                        .mapToObj(x -> new Element(x, y, grid.get(y).charAt(x))))
                .filter(Element::isPath)
                .collect(Collectors.toUnmodifiableSet());
    }

    static final class Result {
        private final String path;
        private final int numberOfSteps;

        Result(String path, int numberOfSteps) {
            this.path = path;
            this.numberOfSteps = numberOfSteps;
        }

        String getPath() {
            return path;
        }

        int getNumberOfSteps() {
            return numberOfSteps;
        }
    }

    private static final class Element {
        private final int x;
        private final int y;
        private final char value;

        private Element(int x, int y, char value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        Map<Element, Direction> getNeighborsRelativeTo(Set<Element> paths) {
            return paths.stream()
                    .filter(this::isNextTo)
                    .collect(MainUtils.mapWithValue(this::getRelativeTo));
        }

        private boolean isNextTo(Element element) {
            return element.x == x && Math.abs(element.y - y) == 1
                    || element.y == y && Math.abs(element.x - x) == 1;
        }

        private Direction getRelativeTo(Element element) {
            if (element.x == x) {
                return element.y < y ? Direction.N : Direction.S;
            }
            return element.x < x ? Direction.W : Direction.E;
        }

        boolean isPath() {
            return value != ' ';
        }

        boolean isStart() {
            return y == 0 && value == '|';
        }

        boolean isLetter() {
            return Character.isAlphabetic(value);
        }

        char getLetter() {
            return value;
        }

        boolean isCorner() {
            return value == '+';
        }

        Direction nextDirection(Element previous, Set<Element> paths) {
            var neighbours = getNeighborsRelativeTo(paths);
            return neighbours.keySet().stream()
                    .filter(element -> !previous.equals(element))
                    .findFirst()
                    .map(neighbours::get)
                    .orElseThrow(() -> new UnexpectedException("direction"));
        }

        boolean isEnd(Set<Element> paths) {
            return getNeighborsRelativeTo(paths).size() == 1 && !isStart();
        }

        Element next(Direction current, Set<Element> paths) {
            var neighbours = getNeighborsRelativeTo(paths);
            return neighbours.keySet().stream()
                    .filter(element -> neighbours.get(element) == current)
                    .findFirst()
                    .orElseThrow(() -> new UnexpectedException(
                            "next element"));
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Element
                    && x == ((Element) o).x
                    && y == ((Element) o).y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private enum Direction {
        N, E, S, W
    }
}
