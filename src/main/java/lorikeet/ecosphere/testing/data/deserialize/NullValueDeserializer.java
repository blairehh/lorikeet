package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.NullValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.InconclusiveError;
import lorikeet.error.NotAValidNullToken;

public class NullValueDeserializer implements ValueDeserializer<NullValue> {

    private final boolean directDeserialization;

    public NullValueDeserializer() {
        this.directDeserialization = true;
    }

    public NullValueDeserializer(boolean directDeserialization) {
        this.directDeserialization = directDeserialization;
    }

    public Err<NullValue> deserialize(TextReader text) {
        final String token = text.nextWord().orElse("");
        if (token.trim().equalsIgnoreCase("null")) {
            return Err.of(new NullValue());
        }
        if (this.directDeserialization) {
            return Err.failure(new NotAValidNullToken());
        }
        return Err.failure(new InconclusiveError(new NotAValidNullToken()));
    }
}
