package lorikeet;

import org.pcollections.TreePVector;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public final class Seq<T> implements List<T> {

    private final TreePVector<T> vector;

    public Seq() {
        this.vector = TreePVector.empty();
    }

    public Seq(TreePVector<T> source) {
        this.vector = source;
    }

    public Seq(Collection<T> collection) {
        this.vector = TreePVector.from(collection);
    }

    public Seq<T> filter(Predicate<T> predicate) {
        return new Seq<>(this.vector.stream().filter(predicate).collect(Collectors.toList()));
    }

    public Seq<T> plus(T value) {
        return new Seq<>(this.vector.plus(value));
    }

    // List
    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        this.vector.replaceAll(operator);
    }

    @Override
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
    public boolean add(T t) {
        return this.vector.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return this.vector.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.vector.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.vector.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.vector.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.vector.retainAll(c);
    }

    @Override
    public void clear() {
        this.vector.clear();
    }

    @Override
    public T get(int index) {
        return this.vector.get(index);
    }

    @Override
    public T set(int index, T element) {
        return this.vector.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        this.vector.add(index, element);
    }

    @Override
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
}
