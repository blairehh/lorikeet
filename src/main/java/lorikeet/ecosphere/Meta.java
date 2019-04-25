package lorikeet.ecosphere;

import lorikeet.Seq;

import java.util.Objects;

public class Meta {
    private final Seq<String> parameters;

    private Meta(Seq<String> parameters) {
        this.parameters = parameters;
    }

    public static Meta none() {
        return new Meta(Seq.empty());
    }

    public static Meta parameters(String... parameterNames) {
        return new Meta(Seq.of(parameterNames));
    }


    public final Seq<String> getParameters() {
        return this.parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        Meta meta = (Meta) o;

        return Objects.equals(this.getParameters(), meta.getParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getParameters());
    }
}
