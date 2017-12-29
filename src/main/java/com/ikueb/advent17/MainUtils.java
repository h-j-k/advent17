package com.ikueb.advent17;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

final class MainUtils {

    private MainUtils() {
        // empty
    }

    static <K, V> Collector<V, ?, Map<K, V>> mapWithKey(
            Function<? super V, ? extends K> keyMapper) {
        return Collectors.collectingAndThen(
                Collectors.toMap(keyMapper, Function.identity()),
                Collections::unmodifiableMap);
    }

    static <K, V> Collector<K, ?, Map<K, V>> mapWithValue(
            Function<? super K, ? extends V> valueMapper) {
        return Collectors.collectingAndThen(
                Collectors.toMap(Function.identity(), valueMapper),
                Collections::unmodifiableMap);
    }

    static <T> Collector<T, ?, Set<T>> toUnmodifiableSet() {
        return Collectors.collectingAndThen(
                Collectors.toSet(),
                Collections::unmodifiableSet);
    }
}
