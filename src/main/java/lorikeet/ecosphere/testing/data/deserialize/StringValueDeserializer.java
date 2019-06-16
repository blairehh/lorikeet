package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.InconclusiveError;
import lorikeet.error.LorikeetException;
import lorikeet.error.NotAValidStringToken;

public class StringValueDeserializer implements ValueDeserializer<StringValue> {

    private final boolean directDeserialization;

    public StringValueDeserializer() {
        this.directDeserialization = true;
    }

    public StringValueDeserializer(boolean directDeserialization) {
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<StringValue> deserialize(TextReader text) {
        final LorikeetException error = this.directDeserialization
            ? new NotAValidStringToken()
            : new InconclusiveError(new NotAValidStringToken());

        return text.nextQuote('\'')
            .map(StringValue::new)
            .mapError(error);
    }
}
