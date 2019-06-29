package lorikeet.lobe.articletesting.data;

import lorikeet.Seq;

import java.util.Objects;

public class NotValue implements Value {

    private final Seq<Value> values;

    public NotValue(Seq<Value> values) {
        this.values = values;
    }

    public Seq<Value> getValues() {
        return this.values;
    }

    @Override
    public boolean isSymbolic() {
        return true;
    }

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }
        final EqualityChecker equality = new EqualityChecker();
        final boolean anyMatched = this.getValues()
            .stream()
            .anyMatch(value -> equality.checkEquality(value, other));

        if (anyMatched) {
            return Equality.NOT_EQUAL;
        }
        return Equality.EQUAL;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        NotValue notValue = (NotValue) o;

        return Objects.equals(this.getValues(), notValue.getValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValues());
    }
}
