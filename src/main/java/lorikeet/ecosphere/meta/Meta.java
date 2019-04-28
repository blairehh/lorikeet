package lorikeet.ecosphere.meta;

import lorikeet.Opt;
import lorikeet.Seq;

import java.util.Objects;

public class Meta {
    private final Seq<ParameterMeta> parameters;

    public Meta(Seq<ParameterMeta> parameters) {
        this.parameters = parameters;
    }

    public static Meta none() {
        return new Meta(Seq.empty());
    }

    public static Meta parameters(String... parameterNames) {
        Seq<ParameterMeta> parameters = Seq.empty();
        for (int i = 0; i < parameterNames.length; i++) {
            final String parameterName = parameterNames[i] == null
                ? null
                : parameterNames[i].trim().isEmpty() ? null : parameterNames[i];
            parameters = parameters.push(new ParameterMeta(i, parameterName, false, false));
        }
        return new Meta(parameters);
    }


    public final Seq<ParameterMeta> getParameters() {
        return this.parameters;
    }

    public Opt<ParameterMeta> findParameter(int index) {
        return this.parameters.fetch(0);
    }

    public ParameterMeta findParameterOrCreate(int index) {
        return this.parameters.fetch(index)
            .orElse(new ParameterMeta(index));
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
