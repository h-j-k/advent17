package com.ikueb.advent17;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

final class Day03 {

    private Day03() {
        // empty
    }

    static int stepsTo(int n) {
        var layer = getLayer(n);
        var end = getLayerMax(layer) - layer;
        var chosen = IntStream.rangeClosed(0, 3)
                .map(i -> end - 2 * layer * i)
                .filter(i -> n >= i || n > i - layer)
                .findFirst()
                .orElseThrow(() -> new UnexpectedException("result"));
        return layer + Math.abs(n - chosen);
    }

    static int getFirstAfter(int min) {
        var map = new HashMap<Integer, Integer>();
        return IntStream.iterate(1, i -> i + 1)
                .dropWhile(i -> find(map, i) <= min)
                .boxed()
                .findFirst()
                .map(map::get)
                .orElseThrow(() -> new UnexpectedException("result"));
    }

    private static int find(Map<Integer, Integer> map, int n) {
        return map.computeIfAbsent(n, k ->
                Math.max(1, Place.around(k).map(i -> find(map, i)).sum()));
    }

    private static int getLayer(int n) {
        var result = 0;
        while (getLayerMax(result) < n) {
            result++;
        }
        return result;
    }

    private static int getLayerMax(int layer) {
        return (int) Math.pow(1 + (double) 2 * layer, 2);
    }

    private enum Place {
        BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT, TOP, TOP_RIGHT, RIGHT;

        static IntStream around(int n) {
            return n == 1 ? IntStream.empty() : get(n).with(n);
        }

        static Place get(int n) {
            for (int i = 1, t = getLayer(n), diff = 2 * t,
                 max = getLayerMax(t); ; i += 2, max -= diff) {
                if (max == n) {
                    return values()[i - 1];
                } else if (max - diff < n) {
                    return values()[i];
                }
            }
        }

        boolean isCorner() {
            return ordinal() % 2 == 0;
        }

        private IntStream with(int n) {
            var layer = getLayer(n);
            var innerLayerMax = getLayerMax(layer - 1);
            if (isCorner()) {
                var inner = innerLayerMax - (layer - 1) * ordinal();
                return this == BOTTOM_RIGHT
                        ? IntStream.of(n - 1, inner, inner + 1)
                        : IntStream.of(n - 1, inner);
            }
            var inner = n - (4 * 2 * layer - ordinal());
            var result = new int[]{n - 1, inner - 1, inner, inner + 1};
            if (n - 1 == innerLayerMax) {
                result = new int[]{n - 1, inner + 1};
            } else if (n - 2 == innerLayerMax || get(n - 1).isCorner()) {
                result[1] = n - 2;
            }
            if (this != BOTTOM && get(n + 1).isCorner()) {
                result = Arrays.copyOf(result, result.length - 1);
            }
            return Arrays.stream(result);
        }
    }
}
