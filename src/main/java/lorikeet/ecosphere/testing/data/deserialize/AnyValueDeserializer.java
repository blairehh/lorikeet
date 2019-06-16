package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.AnyValue;
import lorikeet.ecosphere.testing.data.GenericSymbolicValue;
import lorikeet.ecosphere.testing.reader.TextReader;

public class AnyValueDeserializer implements ValueDeserializer<AnyValue> {

    private final GenericSymbolicDeserializer deserializer = new GenericSymbolicDeserializer();

    @Override
    public Err<AnyValue> deserialize(TextReader reader) {
        final Err<GenericSymbolicValue> deserialization = this.deserializer.deserialize(reader);
        return deserialization
            .filter(AnyValueDeserializer::validValue)
            .map(value -> new AnyValue());
    }

    private static boolean validValue(GenericSymbolicValue value) {
        if (!value.getName().equalsIgnoreCase("any")) {
            return false;
        }
        return value.getArguments().isEmpty();
    }
}
