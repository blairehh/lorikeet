package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.InconclusiveError;
import lorikeet.error.LorikeetException;
import lorikeet.error.NotAValidIdentifier;

public class IdentifierValueDeserializer implements ValueDeserializer<IdentifierValue> {

    private final boolean directDeserialization;

    public IdentifierValueDeserializer() {
        this.directDeserialization = true;
    }

    public IdentifierValueDeserializer(boolean directDeserialization) {
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<IdentifierValue> deserialize(TextReader reader) {
        final LorikeetException error = this.directDeserialization
            ? new NotAValidIdentifier()
            : new InconclusiveError(new NotAValidIdentifier());

        return reader.nextIdentifier()
            .map(IdentifierValue::new)
            .mapError(error);
    }
}
