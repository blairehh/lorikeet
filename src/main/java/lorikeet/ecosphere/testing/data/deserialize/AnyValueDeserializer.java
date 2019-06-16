package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.AnyValue;
import lorikeet.ecosphere.testing.data.GenericSymbolicValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.AnyValueDoesNotAcceptArguments;
import lorikeet.error.AnyValueMustBeNamedAny;
import lorikeet.error.InconclusiveError;

public class AnyValueDeserializer implements ValueDeserializer<AnyValue> {

    private final GenericSymbolicDeserializer deserializer;
    private final boolean directDeserialization;

    public AnyValueDeserializer() {
        this.deserializer = new GenericSymbolicDeserializer();
        this.directDeserialization = true;
    }

    public AnyValueDeserializer(boolean directDeserialization) {
        this.deserializer = new GenericSymbolicDeserializer(directDeserialization);
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<AnyValue> deserialize(TextReader reader) {
        final Err<GenericSymbolicValue> deserialization = this.deserializer.deserialize(reader);
        return deserialization
            .pipe(this::toAnyValue);
    }

    private Err<AnyValue> toAnyValue(GenericSymbolicValue value) {
        if (!value.getName().equalsIgnoreCase("any")) {
            if (this.directDeserialization) {
                return Err.failure(new InconclusiveError(new AnyValueMustBeNamedAny()));
            }
            return Err.failure(new AnyValueMustBeNamedAny());
        }

        if (!value.getArguments().isEmpty()) {
            return Err.failure(new AnyValueDoesNotAcceptArguments());
        }
        return Err.of(new AnyValue());
    }
}
