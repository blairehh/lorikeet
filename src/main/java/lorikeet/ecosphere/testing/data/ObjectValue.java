package lorikeet.ecosphere.testing.data;

import lorikeet.Dict;

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
}
