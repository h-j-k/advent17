package com.ikueb.advent17;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

final class Day14 {

    private Day14() {
        // empty
    }

    static int usedSquares(String input) {
        return convert(input).stream()
                .mapToInt(BitSet::cardinality)
                .sum();
    }

    static int contiguousRegions(String input) {
        List<BitSet> rows = convert(input);
        Set<Element> usedSquares = IntStream.range(0, rows.size())
                .boxed()
                .flatMap(y -> rows.get(y).stream().mapToObj(x -> new Element(x, y)))
                .collect(Collectors.toSet());
        int counter = 0;
        for (Set<Element> region = getNextRegion(usedSquares); !region.isEmpty();
             usedSquares.removeAll(region), region = getNextRegion(usedSquares)) {
            counter++;
        }
        return counter;
    }

    private static Set<Element> getNextRegion(Set<Element> usedSquares) {
        return usedSquares.isEmpty() ? Collections.emptySet()
                : iterate(usedSquares.iterator().next(), new HashSet<>(), usedSquares)
                .collect(MainUtils.toUnmodifiableSet());
    }

    private static Stream<Element> iterate(Element current,
                                           Set<Element> seen,
                                           Set<Element> usedSquares) {
        return Stream.concat(Stream.of(current), current.around()
                .filter(v -> usedSquares.contains(v) && seen.add(v))
                .flatMap(v -> iterate(v, seen, usedSquares)));
    }

    private static List<BitSet> convert(String input) {
        return IntStream.range(0, 128)
                .mapToObj(i -> input + "-" + i)
                .map(Day10::hash)
                .map(Day14::asBitSet)
                .collect(Collectors.toList());
    }

    private static BitSet asBitSet(String hash) {
        return BitSet.valueOf(Stream.of(hash.substring(16), hash.substring(0, 16))
                .mapToLong(v -> Long.parseUnsignedLong(v, 16))
                .toArray());
    }

    private static final class Element {

        private final int x;
        private final int y;

        Element(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Stream<Element> around() {
            return Stream.of(
                    new Element(Math.max(x - 1, 0), y),
                    new Element(x, Math.max(y - 1, 0)),
                    new Element(x + 1, y),
                    new Element(x, y + 1));
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Element && x == ((Element) o).x && y == ((Element) o).y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
