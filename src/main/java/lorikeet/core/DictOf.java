package lorikeet.core;

import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
    public Optional<V> pick(Object key) {
        final V value = this.map.get(key);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public DictOf<K, V> modify(K key, Function<V, V> modifier) {
        final V value = this.map.get(key);
        if (value == null) {
            return this;
        }
        return new DictOf<>(this.map.plus(key, modifier.apply(value)));
    }

    @Override
    public Dict<K, V> modify(K key, Function<V, V> modifierIfFound, Supplier<V> supplierIfNotFound) {
        final V value = this.map.get(key);
        if (value == null) {
            return new DictOf<>(this.map.plus(key, supplierIfNotFound.get()));
        }
        return new DictOf<>(this.map.plus(key, modifierIfFound.apply(value)));
    }

    @Override
    public <T> Dict<K, T> modifyValues(Function<V, T> modifier) {
        final Map<K, T> map = this.map.entrySet()
            .stream()
            .map((keyValue) -> Map.entry(keyValue.getKey(), modifier.apply(keyValue.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new DictOf<>(map);
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

    //////////////////////////////////////////////////////////////////////////////////////////////////
    // Legacy Methods
    //////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    @Deprecated
    public V getOrDefault(Object key, V defaultValue) {
        return this.pick(key).orElse(defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.map.forEach(action);
    }

    @Override
    @Deprecated
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public boolean remove(Object key, Object value) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public boolean replace(K key, V oldValue, V newValue) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public V replace(K key, V value) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return this.map.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return this.map.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return this.map.compute(key, remappingFunction);
    }

    @Override
    @Deprecated
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    @Deprecated
    public V get(Object key) {
        return this.pick(key).orElse(null);
    }

    @Override
    @Deprecated
    public V put(K key, V value) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public V remove(Object key) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public void clear() {
        throw new UseMutableForkCollectionException();
    }

    @Override
    public Set<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return this.map.entrySet();
    }
}
