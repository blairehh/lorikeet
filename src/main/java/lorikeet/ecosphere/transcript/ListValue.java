package lorikeet.ecosphere.transcript;

import lorikeet.Seq;

import java.util.Objects;

public class ListValue implements Value {
    private final Seq<Value> values;

    public ListValue(Seq<Value> values) {
        this.values = values;
    }

    public Seq<Value> getValues() {
        return this.values;
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
