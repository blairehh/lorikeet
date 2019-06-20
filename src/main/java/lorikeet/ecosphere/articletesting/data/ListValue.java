package lorikeet.ecosphere.articletesting.data;

import lorikeet.Opt;
import lorikeet.Seq;

import java.util.Objects;

public class ListValue implements Value {
    private final Seq<Value> values;

    public ListValue(Seq<Value> values) {
        this.values = values;
    }

    public ListValue(Value... values) {
        this.values = Seq.of(values);
    }

    public Seq<Value> getValues() {
        return this.values;
    }

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof ListValue)) {
            return Equality.NOT_EQUAL;
        }

        final ListValue otherValue = (ListValue) other;

        if (this.getValues().size() != otherValue.getValues().size()) {
            return Equality.NOT_EQUAL;
        }

        final EqualityChecker equality = new EqualityChecker();
        for (int i = 0; i < this.getValues().size(); i++) {
            final Opt<Value> otherItem = otherValue.getValues().fetch(i);
            final Value thisValue = this.values.get(i);
            final boolean isEqual = otherItem.map(itemValue -> equality.checkEquality(itemValue, thisValue))
                .orElse(false);

            if (!isEqual) {
                return Equality.NOT_EQUAL;
            }
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

        ListValue listValue = (ListValue) o;

        return Objects.equals(this.getValues(), listValue.getValues());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getValues());
    }
}
