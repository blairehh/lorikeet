package lorikeet.ecosphere.articletesting.data;

import lorikeet.Dict;
import lorikeet.Opt;

import java.util.Map;
import java.util.Objects;

public class ObjectValue implements Value {
    private final String className;
    private final Dict<String, Value> data;

    public ObjectValue(String className, Dict<String, Value> data) {
        this.className = className;
        this.data = data;
    }

    public String getClassName() {
        return this.className;
    }

    public Dict<String, Value> getData() {
        return this.data;
    }

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof ObjectValue)) {
            return Equality.NOT_EQUAL;
        }

        final ObjectValue otherValue = (ObjectValue) other;
        if (!this.getClassName().equals(otherValue.getClassName())) {
            return Equality.NOT_EQUAL;
        }

        if (this.getData().size() != otherValue.getData().size()) {
            return Equality.NOT_EQUAL;
        }

        final EqualityChecker equality = new EqualityChecker();
        for (Map.Entry<String, Value> item : this.getData().entrySet()) {
            final Opt<Value> otherItem = otherValue.getData().find(item.getKey());
            final boolean isEqual = otherItem.map(itemValue -> equality.checkEquality(itemValue, item.getValue()))
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

        ObjectValue that = (ObjectValue) o;

        return Objects.equals(this.getClassName(), that.getClassName())
            && Objects.equals(this.getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getClassName(), this.getData());
    }

    @Override
    public String toString() {
        return String.format("%s={%s}", this.className, this.data);
    }
}
