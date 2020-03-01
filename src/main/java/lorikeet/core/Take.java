package lorikeet.core;

import java.util.Objects;

public class Take<T, E> implements Duo<E, Seq<T>>  {
    private final E element;
    private final Seq<T> seq;

    public Take(E element, Seq<T> seq) {
        this.element = element;
        this.seq = seq;
    }

    public E element() {
        return this.element;
    }

    public Seq<T> seq() {
        return this.seq;
    }

    @Override
    public E left() {
        return this.element;
    }

    @Override
    public Seq<T> right() {
        return this.seq;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Take<?, ?> take = (Take<?, ?>) o;

        return Objects.equals(this.element, take.element)
            && Objects.equals(this.seq, take.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.element, this.seq);
    }
}
