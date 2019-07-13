package lorikeet;

import org.pcollections.TreePVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class Seq<T> implements List<T>, May<T> {

    private final TreePVector<T> vector;
    private final Exception error;

    public Seq() {
        this.vector = TreePVector.empty();
        this.error = null;
    }

    public Seq(TreePVector<T> source) {
        this.vector = source;
        this.error = null;
    }

    public Seq(Collection<T> collection) {
        this.vector = TreePVector.from(collection);
        this.error = null;
    }

    public Seq(Exception err) {
        this.vector = TreePVector.empty();
        this.error = err;
    }

    public static <X> Seq<X> empty() {
        return new Seq<>();
    }

    public static <X> Seq<X> of(List<X> list) {
        return new Seq<>(list);
    }

    public static <X> Seq<X> of(X... values) {
        return new Seq<>(Arrays.asList(values));
    }

    public static <X> Seq<X> unique(List<X> list) {
        Seq<X> seq = Seq.empty();
        for (X value : list) {
            seq = seq.push(value);
        }
        return seq;
    }

    public static <X,A> Seq<X> unique(List<A> list, Function<A, X> mapper) {
        Seq<X> seq = Seq.empty();
        for (A item : list) {
            final X value = mapper.apply(item);
            if (!seq.contains(value)) {
                seq = seq.push(value);
            }
        }
        return seq;
    }

    public static <X> Collector<X, List<X>, Seq<X>> collector() {
        return Collector.of(
            ArrayList::new,
            (List<X> list, X item) -> {list.add(item);},
            (List<X> listA, List<X> listB) -> {listA.addAll(listB); return listA;},
             Seq::of,
            Collector.Characteristics.UNORDERED
        );
    }

    public Err<T> asErr() {
        if (this.error != null) {
            return Err.failure(this.error);
        }
        if (this.isEmpty()) {
            return Err.failure();
        }
        return Err.of(this.vector.get(0));
    }

    public Seq<T> filter(Predicate<? super T> predicate) {
        return this.vector.stream().filter(predicate).collect(Seq.collector());
    }

    public Opt<T> random() {
        if (this.isEmpty()) {
            return Opt.empty();
        }
        final int index = ThreadLocalRandom.current().nextInt(0, this.size());
        return Opt.of(this.get(index));
    }

    public Opt<T> first() {
        if (this.vector.isEmpty()) {
            return Opt.empty();
        }
        return Opt.ofNullable(this.vector.get(0));
    }

    public Seq<T> push(T value) {
        return new Seq<>(this.vector.plus(value));
    }

    public Seq<T> push(Seq<T> seq) {
        return new Seq<T>(this.vector.plusAll(seq));
    }

    public Seq<T> push(May<T> may) {
        return may.map(this::push).orElse(this);
    }

    public Opt<T> fetch(int index) {
        if (index >= this.size()) {
            return Opt.empty();
        }
        return Opt.ofNullable(this.get(index));
    }

    public boolean anyMatch(Predicate<T> predicate) {
        return this.vector.stream().anyMatch(predicate);
    }

    public Seq<T> drop(int index) {
        return new Seq<>(this.vector.minus(index));
    }

    public Seq<T> drop(T value) {
        return new Seq<>(this.vector.minus(value));
    }

    public <U> Seq<U> map(Function<? super T, ? extends U> functor) {
        return new Seq<>(this.vector.stream().map(functor).collect(Collectors.toList()));
    }

    public <V> Dict<T, V> mapify(Function<T, V> mapper) {
        Dict<T, V> map = Dict.empty();
        for (T value : this.vector) {
            map = map.push(value, mapper.apply(value));
        }
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Seq<?> seq = (Seq<?>) o;

        return Objects.equals(this.vector, seq.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.vector);
    }

    @Override
    public T orPanic() {
        return this.first().orPanic();
    }

    @Override
    public boolean isPresent() {
        return !this.isEmpty();
    }

    @Override
    public May<T> then(Consumer<? super T> action) {
        return this.first().then(action);
    }

    @Override
    public May<T> or(Supplier<? extends May<? extends T>> supplier) {
        return this.first().or(supplier);
    }

    @Override
    public T orElse(T other) {
        return this.first().orElse(other);
    }

    /*
            Methods for List<T>
        */
    @Override
    @Deprecated
    public void replaceAll(UnaryOperator<T> operator) {
        this.vector.replaceAll(operator);
    }

    @Override
    @Deprecated
    public void sort(Comparator<? super T> c) {
        this.vector.sort(c);
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.vector.spliterator();
    }

    @Override
    public int size() {
        return this.vector.size();
    }

    @Override
    public boolean isEmpty() {
        return this.vector.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.vector.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return this.vector.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.vector.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return this.vector.toArray(a);
    }

    @Override
    @Deprecated
    public boolean add(T t) {
        return this.vector.add(t);
    }

    @Override
    @Deprecated
    public boolean remove(Object o) {
        return this.vector.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.vector.containsAll(c);
    }

    @Override
    @Deprecated
    public boolean addAll(Collection<? extends T> c) {
        return this.vector.addAll(c);
    }

    @Override
    @Deprecated
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.vector.addAll(index, c);
    }

    @Override
    @Deprecated
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    @Deprecated
    public boolean retainAll(Collection<?> c) {
        return this.vector.retainAll(c);
    }

    @Override
    @Deprecated
    public void clear() {
        this.vector.clear();
    }

    @Override
    public T get(int index) {
        return this.vector.get(index);
    }

    @Override
    @Deprecated
    public T set(int index, T element) {
        return this.vector.set(index, element);
    }

    @Override
    @Deprecated
    public void add(int index, T element) {
        this.vector.add(index, element);
    }

    @Override
    @Deprecated
    public T remove(int index) {
        return this.vector.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.vector.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.vector.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.vector.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return this.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.vector.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return this.vector.toString();
    }
}
