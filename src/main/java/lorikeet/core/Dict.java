package lorikeet.core;

public interface Dict<K, V> {
    Dict<K, V> push(K key, V value);
    Dict<K, V> pop(K key);
}
