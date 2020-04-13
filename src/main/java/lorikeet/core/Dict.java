package lorikeet.core;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Dict<K, V> extends Map<K, V> {
    Dict<K, V> push(K key, V value);
    Dict<K, V> push(K key, V valueIfKeyNotFound, Function<V, V> valueModifier);
    Dict<K, V> pop(K key);

    Dict<K, V> modify(K key, Function<V, V> modifier);
    Dict<K, V> modify(K key, Function<V, V> modifierIfFound, Supplier<V> supplierIfNotFound);
    <T> Dict<K, T> modifyValues(Function<V, T> modifier);

    Optional<V> pick(Object key);
}
