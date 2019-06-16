package lorikeet.ecosphere.testing.data;

import lorikeet.Dict;
import lorikeet.Opt;
import lorikeet.Seq;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MapValue implements Value {
    private final Dict<Value, Value> data;

    public MapValue(Dict<Value, Value> data) {
        this.data = data;
    }

    public Dict<Value, Value> getData() {
        return this.data;
    }

    @Override
    public Equality equality(Value other) {
        if (other.isSymbolic()) {
            return Equality.UNKNOWN;
        }

        if (!(other instanceof MapValue)) {
            return Equality.NOT_EQUAL;
        }

        final MapValue otherMap = (MapValue) other;

        final Set<Map.Entry<Value, Value>> otherEntrySet = otherMap.getData().entrySet();
        final Set<Map.Entry<Value, Value>> thisEntrySet = this.getData().entrySet();

        if (isEntrySetNotEqual(thisEntrySet, otherEntrySet)) {
            return Equality.NOT_EQUAL;
        }

        if (isEntrySetNotEqual(otherEntrySet, thisEntrySet)) {
            return Equality.NOT_EQUAL;
        }

        return Equality.EQUAL;
    }

    private static boolean isEntrySetNotEqual(Set<Map.Entry<Value, Value>> a, Set<Map.Entry<Value, Value>> b) {
        final EqualityChecker equality = new EqualityChecker();
        for (Map.Entry<Value, Value> item : a) {
            final Opt<Map.Entry<Value, Value>> matchedEntry = b.stream()
                .filter(otherEntry -> equality.checkEquality(otherEntry.getKey(), item.getKey()))
                .collect(Seq.collector())
                .first();

            final boolean isEqual = matchedEntry.map(entry -> equality.checkEquality(entry.getValue(), item.getValue()))
                .orElse(false);

            if (!isEqual) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        MapValue mapValue = (MapValue) o;

        return Objects.equals(this.getData(), mapValue.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getData());
    }
}
