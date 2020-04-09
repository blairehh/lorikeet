package lorikeet.core;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface Dict<K, V> extends Map<K, V> {
    Dict<K, V> push(K key, V value);
    Dict<K, V> push(K key, V valueIfKeyNotFound, Function<V, V> valueModifier);
    Dict<K, V> pop(K key);

    <T> Dict<K, T> modifyValues(Function<V, T> modifier);

    Optional<V> pick(Object key);
}
