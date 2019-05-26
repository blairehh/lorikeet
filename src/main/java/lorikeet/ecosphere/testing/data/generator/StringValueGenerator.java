package lorikeet.ecosphere.testing.data.generator;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.error.StringValueMustBeOneCharacterToGenerateChar;
import lorikeet.error.ValueGeneratorDoesNotSupportType;
import lorikeet.error.ValueGeneratorDoesNotSupportValue;

public class StringValueGenerator implements ValueGenerator {

    @Override
    public <T> Err<T> generate(Class<T> classDef, Value value) {
        if (!(value instanceof StringValue)) {
            return Err.failure(new ValueGeneratorDoesNotSupportValue());
        }
        final StringValue stringValue = (StringValue)value;

        if (classDef.equals(String.class)) {
            return Err.of(classDef.cast(generateString(stringValue)));
        }

        if (classDef.equals(Character.class)) {
            return (Err<T>)generateCharacter(stringValue);
        }

        return Err.failure(new ValueGeneratorDoesNotSupportType());
    }

    private static String generateString(StringValue stringValue) {
        return stringValue.getValue();
    }

    private static Err<Character> generateCharacter(StringValue stringValue) {
        if (stringValue.getValue().length() != 1) {
            return Err.failure(new StringValueMustBeOneCharacterToGenerateChar());
        }
        return Err.of(stringValue.getValue().charAt(0));
    }
}
