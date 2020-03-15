package lorikeet.core;

import java.util.function.Function;

public interface Dict<K, V> {
    Dict<K, V> push(K key, V value);
    Dict<K, V> push(K key, V valueIfKeyNotFound, Function<V, V> valueModifier);
    Dict<K, V> pop(K key);
}
