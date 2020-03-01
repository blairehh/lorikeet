package lorikeet.core;

import java.util.Objects;

public class DuoOf<L, R> implements Duo<L, R> {
    private final L left;
    private final R right;

    public DuoOf(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public L left() {
        return this.left;
    }

    @Override
    public R right() {
        return this.right;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        DuoOf<?, ?> duoOf = (DuoOf<?, ?>) o;

        return Objects.equals(this.left(), duoOf.left())
            && Objects.equals(this.right(), duoOf.right());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.left(), this.right());
    }
}
