package lorikeet.ecosphere.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.articletesting.data.BoolValue;
import lorikeet.ecosphere.articletesting.reader.TextReader;
import lorikeet.error.InconclusiveError;
import lorikeet.error.NotAValidBooleanValue;

public class BoolValueDeserializer implements ValueDeserializer<BoolValue> {

    private final boolean directDeserialization;

    public BoolValueDeserializer() {
        this.directDeserialization = false;
    }

    public BoolValueDeserializer(boolean directDeserialization) {
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<BoolValue> deserialize(TextReader text) {
        final String token = text.nextWord().orElse("");
        if (token.trim().equalsIgnoreCase("true")) {
            return Err.of(new BoolValue(true));
        }
        if (token.trim().equalsIgnoreCase("false")) {
            return Err.of(new BoolValue(false));
        }
        if (this.directDeserialization) {
            return Err.failure(new NotAValidBooleanValue(token));
        }
        return Err.failure(new InconclusiveError(new NotAValidBooleanValue(token)));
    }
}
