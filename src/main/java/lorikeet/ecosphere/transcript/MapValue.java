package lorikeet.ecosphere.transcript;

import lorikeet.Dict;

import java.util.Objects;

public class MapValue implements Value {
    private final Dict<Value, Value> data;

    public MapValue(Dict<Value, Value> data) {
        this.data = data;
    }

    public Dict<Value, Value> getData() {
        return this.data;
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
