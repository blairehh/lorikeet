package lorikeet.lobe.articletesting.data.generator;

import lorikeet.Err;
import lorikeet.lobe.articletesting.data.BoolValue;
import lorikeet.lobe.articletesting.data.Value;
import lorikeet.error.BooleanValueIsRequiredToGenerateBoolean;
import lorikeet.error.ValueGeneratorDoesNotSupportJavaType;

@SuppressWarnings("unchecked")
public class BoolValueGenerator implements ValueGenerator {

    @Override
    public <T> Err<T> generate(Class<T> classDef, Value value) {
        if (!classDef.equals(Boolean.class)) {
            return Err.failure(new ValueGeneratorDoesNotSupportJavaType());
        }

        if (!(value instanceof BoolValue)) {
            return Err.failure(new BooleanValueIsRequiredToGenerateBoolean());
        }
        return (Err<T>)Err.of(((BoolValue)value).getValue());
    }
}
