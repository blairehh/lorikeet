package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.NullValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.NotAValidNullToken;

public class NullValueDeserializer implements ValueDeserializer<NullValue> {
    public Err<NullValue> deserialize(TextReader text) {
        final String token = text.nextWord().orElse("");
        if (token.trim().equalsIgnoreCase("null")) {
            return Err.of(new NullValue());
        }
        return Err.failure(new NotAValidNullToken());
    }
}
