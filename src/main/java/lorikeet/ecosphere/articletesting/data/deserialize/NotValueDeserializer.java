package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.articletesting.data.GenericSymbolicValue;
import lorikeet.ecosphere.articletesting.data.NotValue;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import lorikeet.error.InconclusiveError;
import lorikeet.error.NotValueMustBeNamedNot;
import lorikeet.error.NotValueMustBeSuppliedWithArguments;

public class NotValueDeserializer implements ValueDeserializer<NotValue> {

    private final GenericSymbolicDeserializer deserializer;
    private final boolean directDeserialization;

    public NotValueDeserializer() {
        this.deserializer = new GenericSymbolicDeserializer();
        this.directDeserialization = true;
    }

    public NotValueDeserializer(boolean directDeserialization) {
        this.deserializer = new GenericSymbolicDeserializer(directDeserialization);
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<NotValue> deserialize(TextReader reader) {
        final Err<GenericSymbolicValue> deserialization = this.deserializer.deserialize(reader);
        return deserialization
            .pipe(this::toNotValue);
    }

    private Err<NotValue> toNotValue(GenericSymbolicValue value) {
        if (!value.getName().equalsIgnoreCase("not")) {
            if (this.directDeserialization) {
                return Err.failure(new NotValueMustBeNamedNot());
            }
            return Err.failure(new InconclusiveError(new NotValueMustBeNamedNot()));
        }

        if (value.getArguments().isEmpty()) {
            return Err.failure(new NotValueMustBeSuppliedWithArguments());
        }
        return Err.of(new NotValue(value.getArguments()));
    }
}
