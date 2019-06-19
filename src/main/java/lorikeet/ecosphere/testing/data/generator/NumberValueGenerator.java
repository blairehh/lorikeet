package lorikeet.ecosphere.testing.data.generator;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.error.ValueGeneratorDoesNotSupportType;
import lorikeet.error.ValueGeneratorDoesNotJavaType;

@SuppressWarnings("unchecked")
public class NumberValueGenerator implements ValueGenerator {

    @Override
    public <T> Err<T> generate(Class<T> classDef, Value value) {
        if (!(value instanceof NumberValue)) {
            return Err.failure(new ValueGeneratorDoesNotJavaType());
        }

        final NumberValue numberValue = (NumberValue)value;

        if (classDef.equals(Short.class)) {
            return (Err<T>)generateShort(numberValue);
        }

        if (classDef.equals(Integer.class)) {
            return (Err<T>)generateInteger(numberValue);
        }

        if (classDef.equals(Long.class)) {
            return (Err<T>)generateLong(numberValue);
        }

        if (classDef.equals(Float.class)) {
            return (Err<T>)generateFloat(numberValue);
        }

        if (classDef.equals(Double.class)) {
            return (Err<T>)generateDouble(numberValue);
        }

        return Err.failure(new ValueGeneratorDoesNotSupportType());
    }

    private static Err<Short> generateShort(NumberValue value) {
        return Err.of(value.getValue().shortValue());
    }

    private static Err<Integer> generateInteger(NumberValue value) {
        return Err.of(value.getValue().intValue());
    }

    private static Err<Long> generateLong(NumberValue value) {
        return Err.of(value.getValue().longValue());
    }

    private static Err<Float> generateFloat(NumberValue value) {
        return Err.of(value.getValue().floatValue());
    }

    private static Err<Double> generateDouble(NumberValue value) {
        return Err.of(value.getValue().doubleValue());
    }

}
