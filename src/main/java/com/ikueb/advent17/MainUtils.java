package com.ikueb.advent17;

import java.util.Map;
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
        return (Collector<V, A, M>)
                Collectors.toUnmodifiableMap(keyMapper, Function.identity());
    }

    @SuppressWarnings("unchecked")
    static <K, A, V, M extends Map<K, V>> Collector<K, A, M> mapWithValue(
            Function<? super K, ? extends V> valueMapper) {
        return (Collector<K, A, M>)
                Collectors.toUnmodifiableMap(Function.identity(), valueMapper);
    }

}
