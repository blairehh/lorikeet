package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.NullValue;
import lorikeet.ecosphere.testing.reader.TextReader;

public class NullValueDeserializer implements ValueDeserializer<NullValue> {
    public Opt<NullValue> deserialize(TextReader text) {
        final String token = text.nextWord().orElse("");
        if (token.trim().equalsIgnoreCase("null")) {
            return Opt.of(new NullValue());
        }
        return Opt.empty();
    }
}
