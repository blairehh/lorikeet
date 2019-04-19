package lorikeet;

import java.util.Objects;

public final class Duo<L, R> {
    private final L left;
    private final R right;

    public Duo(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <A, B> Duo<A, B> of(A left, B right) {
        return new Duo<>(left, right);
    }

    public L getLeft() {
        return this.left;
    }

    public R getRight() {
        return this.right;
    }

    public L getKey() {
        return this.left;
    }

    public R getValue() {
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

        Duo<?, ?> duo = (Duo<?, ?>) o;

        return Objects.equals(this.getLeft(), duo.getLeft())
            && Objects.equals(this.getRight(), duo.getRight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getLeft(), this.getRight());
    }
}
