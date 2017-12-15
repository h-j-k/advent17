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
        TreeSet<Element> usedSquares = IntStream.range(0, rows.size())
                .boxed()
                .flatMap(y -> rows.get(y).stream().mapToObj(x -> new Element(x, y)))
                .collect(Collectors.toCollection(TreeSet::new));
        int counter = 0;
        for (Set<Element> region = getNextRegion(usedSquares); !region.isEmpty();
             usedSquares.removeAll(region), region = getNextRegion(usedSquares)) {
            counter++;
        }
        return counter;
    }

    private static Set<Element> getNextRegion(TreeSet<Element> usedSquares) {
        return usedSquares.isEmpty() ? Collections.emptySet()
                : iterate(usedSquares.first(), new TreeSet<>(), usedSquares)
                .collect(Collectors.toSet());
    }

    private static Stream<Element> iterate(Element current,
                                           Set<Element> seen,
                                           Set<Element> usedSquares) {
        Set<Element> selected = current.around()
                .filter(v -> usedSquares.contains(v) && seen.add(v))
                .collect(Collectors.toSet());
        return Stream.concat(selected.stream(),
                selected.stream().flatMap(v -> iterate(v, seen, usedSquares)));
    }

    private static List<BitSet> convert(String input) {
        return IntStream.range(0, 128)
                .mapToObj(i -> input + "-" + i)
                .map(Day10::hash)
                .map(Day14::asBitSet)
                .collect(Collectors.toList());
    }

    private static BitSet asBitSet(String hash) {
        BitSet result = new BitSet(128);
        String binaryString = hash.chars()
                .mapToObj(c -> Character.toString((char) c))
                .map(Day14::toBinaryString)
                .collect(Collectors.joining());
        IntStream.range(0, binaryString.length())
                .filter(i -> binaryString.charAt(i) == '1')
                .forEach(result::set);
        return result;
    }

    private static String toBinaryString(String c) {
        return String.format("%4s", Integer.toBinaryString(Integer.parseInt(c, 16)));
    }

    private static final class Element implements Comparable<Element> {

        private static final Comparator<Element> COMPARATOR =
                Comparator.comparing(Element::getY).thenComparing(Element::getX);

        private final int x;
        private final int y;

        Element(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int getX() {
            return x;
        }

        private int getY() {
            return y;
        }

        Stream<Element> around() {
            return Stream.of(this,
                    new Element(Math.max(x - 1, 0), y),
                    new Element(x, Math.max(y - 1, 0)),
                    new Element(x + 1, y),
                    new Element(x, y + 1));
        }

        @Override
        public int compareTo(Element o) {
            return COMPARATOR.compare(this, o);
        }

        @Override
        public boolean equals(Object o) {
            return o == this || (o instanceof Element
                    && x == ((Element) o).x && y == ((Element) o).y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("%3d, %3d", x, y);
        }
    }
}
