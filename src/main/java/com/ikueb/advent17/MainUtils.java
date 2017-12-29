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

    @SuppressWarnings("unchecked")
    static <K, A, V, M extends Map<K, V>> Collector<V, A, M> mapWithKey(
            Function<? super V, ? extends K> keyMapper) {
        return (Collector<V, A, M>) Collectors.collectingAndThen(
                Collectors.toMap(keyMapper, Function.identity()),
                Collections::unmodifiableMap);
    }

    @SuppressWarnings("unchecked")
    static <K, A, V, M extends Map<K, V>> Collector<K, A, M> mapWithValue(
            Function<? super K, ? extends V> valueMapper) {
        return (Collector<K, A, M>) Collectors.collectingAndThen(
                Collectors.toMap(Function.identity(), valueMapper),
                Collections::unmodifiableMap);
    }

    @SuppressWarnings("unchecked")
    static <T, A, S extends Set<T>> Collector<T, A, S> toUnmodifiableSet() {
        return (Collector<T, A, S>) Collectors.collectingAndThen(
                Collectors.toSet(),
                Collections::unmodifiableSet);
    }
}
