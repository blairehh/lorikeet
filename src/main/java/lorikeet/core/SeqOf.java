package lorikeet.core;

import org.pcollections.TreePVector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class SeqOf<T> implements Seq<T> {

    private final TreePVector<T> vector;

    private SeqOf(TreePVector<T> copy) {
        this.vector = copy;
    }

    public SeqOf() {
        this(TreePVector.empty());
    }

    public SeqOf(T... values) {
        this(TreePVector.from(List.of(values)));
    }

    public SeqOf(Collection<T> collection) {
        this(TreePVector.from(collection));
    }

    public SeqOf(T value) {
        this(TreePVector.singleton(value));
    }

    public SeqOf(Fallible<T> fallible) {
        if (fallible.success()) {
            this.vector = TreePVector.singleton(fallible.orPanic());
        } else {
            this.vector = TreePVector.empty();
        }
    }

    @Override
    public final long count() {
        return this.vector.size();
    }

    @Override
    public final long count(Predicate<? super T> countPredicate) {
        return this.vector.stream()
            .filter(countPredicate)
            .count();
    }

    @Override
    public final SeqOf<T> affix(T value) {
        return new SeqOf<>(this.vector.plus(value));
    }

    @Override
    public final Seq<T> affix(Collection<? extends T> elements) {
        return new SeqOf<>(this.vector.plusAll(elements));
    }

    @Override
    public final Optional<T> pick(int index) {
        try {
          return Optional.of(this.vector.get(index));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    @Override
    public final Seq<T> pick(Predicate<? super T> pickPredicate) {
        return this.vector.stream()
            .filter(pickPredicate)
            .collect(new SeqCollector<>());
    }

    @Override
    public final Seq<T> drop(int index) {
        try {
            return new SeqOf<>(this.vector.minus(index));
        } catch (IndexOutOfBoundsException e) {
            return this;
        }
    }

    @Override
    public final Seq<T> drop(T element) {
        return new SeqOf<>(this.vector.minus(element));
    }

    @Override
    public final Seq<T> drop(Collection<? extends T> elements) {
        return new SeqOf<>(this.vector.minusAll(elements));
    }

    @Override
    public final Seq<T> drop(Predicate<? super T> dropPredicate) {
        return this.vector.stream()
            .filter(dropPredicate.negate())
            .collect(new SeqCollector<>());
    }

    @Override
    public Take<T, Optional<T>> take(int index) {
        return new Take<>(this.pick(index), this.drop(index));
    }

    @Override
    public Take<T, Seq<T>> take(Predicate<? super T> takePredicate) {
        return new Take<>(this.pick(takePredicate), this.drop(takePredicate));
    }

    @Override
    public final <X> Seq<X> remodel(Function<? super T,? extends X> remodeller) {
        return this.vector.stream()
            .map(remodeller)
            .collect(new SeqCollector<>());
    }

    @Override
    public final Seq<T> modify(Function<T, T> modifier) {
        return this.vector.stream()
            .map(modifier)
            .collect(new SeqCollector<>());
    }

    @Override
    public final Seq<T> modify(Predicate<T> modifyPredicate, Function<T, T> modifier) {
        final Function<T, T> mapper = (element) -> modifyPredicate.test(element)
            ? modifier.apply(element)
            : element;
        return this.vector.stream()
            .map(mapper)
            .collect(new SeqCollector<>());
    }

    @Override
    public final List<T> forkMutable() {
        final List<T> list = new ArrayList<>((int)this.count());
        list.addAll(this.vector);
        return list;
    }

    @Override
    public Stream<T> stream() {
        return this.vector.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return this.vector.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        this.vector.forEach(action);
    }

    @Override
    @Deprecated
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public void sort(Comparator<? super T> c) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public Spliterator<T> spliterator() {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public int size() {
        return this.vector.size();
    }

    @Override
    @Deprecated
    public boolean isEmpty() {
        return this.vector.isEmpty();
    }

    @Override
    @Deprecated
    public boolean contains(Object o) {
        return this.vector.contains(o);
    }

    @Override
    @Deprecated
    public Iterator<T> iterator() {
        return this.vector.iterator();
    }

    @Override
    @Deprecated
    public Object[] toArray() {
        return this.vector.toArray();
    }

    @Override
    @Deprecated
    public <T1> T1[] toArray(T1[] t1s) {
        return this.vector.toArray(t1s);
    }

    @Override
    @Deprecated
    public boolean add(T t) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public boolean remove(Object o) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public boolean containsAll(Collection<?> collection) {
        return this.vector.containsAll(collection);
    }

    @Override
    @Deprecated
    public boolean addAll(Collection<? extends T> collection) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public boolean removeAll(Collection<?> collection) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public boolean retainAll(Collection<?> collection) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public void clear() {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public T get(int i) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public T set(int i, T t) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public void add(int i, T t) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public T remove(int i) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public int indexOf(Object o) {
        return this.vector.indexOf(o);
    }

    @Override
    @Deprecated
    public int lastIndexOf(Object o) {
        return this.vector.lastIndexOf(o);
    }

    @Override
    @Deprecated
    public ListIterator<T> listIterator() {
        return this.vector.listIterator();
    }

    @Override
    @Deprecated
    public ListIterator<T> listIterator(int i) {
        return this.vector.listIterator(i);
    }

    @Override
    @Deprecated
    public List<T> subList(int i, int i1) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    @Deprecated
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        return this.vector.toArray(generator);
    }

    @Override
    @Deprecated
    public boolean removeIf(Predicate<? super T> filter) {
        throw new UseMutableForkCollectionException();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        SeqOf<?> seq = (SeqOf<?>) o;

        return Objects.equals(this.vector, seq.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.vector);
    }
}
