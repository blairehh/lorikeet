package lorikeet.core;

import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class DictOf<K, V> implements Dict<K, V> {
    private final HashPMap<K, V> map;

    public DictOf() {
        this.map = HashTreePMap.empty();
    }

    public DictOf(Map<? extends K, ? extends V> source) {
        this.map = HashTreePMap.from(source);
    }

    private DictOf(HashPMap<K, V> base) {
        this.map = base;
    }

    @Override
    public Dict<K, V> push(K key, V value) {
        return new DictOf<>(this.map.plus(key, value));
    }

    @Override
    public Dict<K, V> push(K key, V valueIfNotFound, Function<V, V> valueModifier) {
        if (!this.map.containsKey(key)) {
            return this.push(key, valueIfNotFound);
        }
        return this.push(key, valueModifier.apply(this.map.get(key)));
    }

    @Override
    public Dict<K, V> pop(Object key) {
        return new DictOf<>(this.map.minus(key));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        DictOf<?, ?> dictOf = (DictOf<?, ?>) o;

        return Objects.equals(this.map, dictOf.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.map);
    }
}
