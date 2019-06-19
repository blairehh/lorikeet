package lorikeet.ecosphere.testing.data.generator;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.Value;
import lorikeet.error.CouldNotFindEnumConstantFromEnumIdentifierValue;
import lorikeet.error.EnumIdentifierValueClassMismatch;
import lorikeet.error.IdentifierValueRequiredToGenerateEnum;
import lorikeet.error.ValueGeneratorDoesNotSupportJavaType;

public class EnumIdentifierValueGenerator implements ValueGenerator {
    @Override
    public <T> Err<T> generate(Class<T> classDef, Value value) {
        if (!classDef.isEnum()) {
            return Err.failure(new ValueGeneratorDoesNotSupportJavaType());
        }

        if (!(value instanceof IdentifierValue)) {
            return Err.failure(new IdentifierValueRequiredToGenerateEnum());
        }

        final String identifier = ((IdentifierValue)value).getIdentifier();
        if (identifier.contains(".")) {
            return this.generateAsWholeIdentifier(classDef, identifier);
        }
        return this.generateAsJustConstantName(classDef, identifier);
    }

    private <T> Err<T> generateAsWholeIdentifier(Class<T> classDef, String identifier) {
        final String className = identifier.substring(0, identifier.lastIndexOf("."));
        if (!className.equals(classDef.getName())) {
            return Err.failure(new EnumIdentifierValueClassMismatch());
        }
        return this.generateAsJustConstantName(classDef, identifier.substring(identifier.lastIndexOf(".") + 1));
    }

    private <T> Err<T> generateAsJustConstantName(Class<T> classDef, String identifier) {
        for (T constant : classDef.getEnumConstants()) {
            final Enum<?> value = (Enum<?>)constant;
            if (value.name().equals(identifier)) {
                return Err.of(constant);
            }
        }
        return Err.failure(new CouldNotFindEnumConstantFromEnumIdentifierValue());
    }
}
