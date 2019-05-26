package lorikeet.ecosphere.testing.data.generator;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.error.ValueGeneratorDoesNotSupportType;
import lorikeet.error.ValueGeneratorDoesNotSupportValue;

@SuppressWarnings("unchecked")
public class BoolValueGenerator implements ValueGenerator {

    @Override
    public <T> Err<T> generate(Class<T> classDef, Value value) {
        if (!(value instanceof BoolValue)) {
            return Err.failure(new ValueGeneratorDoesNotSupportValue());
        }

        if (!classDef.equals(Boolean.class)) {
            return Err.failure(new ValueGeneratorDoesNotSupportType());
        }

        return (Err<T>)Err.of(((BoolValue)value).getValue());
    }
}
