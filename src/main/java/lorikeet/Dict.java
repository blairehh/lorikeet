package lorikeet;

import org.pcollections.HashPMap;
import org.pcollections.HashTreePMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Dict<K, V> implements Map<K, V> {
    private final HashPMap<K, V> map;

    public Dict() {
        this.map = HashTreePMap.empty();
    }

    public Dict(Map<K, V> map) {
        this.map = HashTreePMap.from(map);
    }

    private Dict(HashPMap<K, V> map) {
        this.map = map;
    }

    public static <A,B> Dict<A,B> empty() {
        return new Dict<>();
    }

    public static <A,B> Dict<A,B> of(Map<A,B> map) {
        return new Dict<>(map);
    }

    public static <A, B> Dict<A, B> of(A key, B value) {
        return (Dict<A,B>)Dict.empty()
            .push(key, value);
    }

    public static <A, B> Dict<A, B> of(A key1, B value1, A key2, B value2) {
        return (Dict<A,B>)Dict.empty()
            .push(key1, value1)
            .push(key2, value2);
    }



    public Dict<K, V> push(K key, V value) {
        return new Dict<>(this.map.plus(key, value));
    }

    public Optional<V> find(K key) {
        return Optional.ofNullable(this.map.get(key));
    }

    /*
    Map
     */

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return this.map.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        this.map.forEach(action);
    }

    @Override
    @Deprecated
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.map.replaceAll(function);
    }

    @Override
    @Deprecated
    public V putIfAbsent(K key, V value) {
        return this.map.putIfAbsent(key, value);
    }

    @Override
    @Deprecated
    public boolean remove(Object key, Object value) {
        return this.map.remove(key, value);
    }

    @Override
    @Deprecated
    public boolean replace(K key, V oldValue, V newValue) {
        return this.map.replace(key, oldValue, newValue);
    }

    @Override
    @Deprecated
    public V replace(K key, V value) {
        return this.map.replace(key, value);
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
        return this.compute(key, remappingFunction);
    }

    @Override
    @Deprecated
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return this.map.merge(key, value, remappingFunction);
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
    public V get(Object key) {
        return this.map.get(key);
    }

    @Override
    @Deprecated
    public V put(K key, V value) {
        return this.map.put(key, value);
    }

    @Override
    @Deprecated
    public V remove(Object key) {
        return this.map.remove(key);
    }

    @Override
    @Deprecated
    public void putAll(Map<? extends K, ? extends V> m) {
        this.map.putAll(m);
    }

    @Override
    @Deprecated
    public void clear() {
        this.map.clear();
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
