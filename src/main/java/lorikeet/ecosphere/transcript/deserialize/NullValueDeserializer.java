package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.NullValue;
import lorikeet.ecosphere.transcript.TextReader;

public class NullValueDeserializer implements ValueDeserializer<NullValue> {
    public Opt<NullValue> deserialize(TextReader text) {
        final String token = text.nextWord().orElse("");
        if (token.trim().equalsIgnoreCase("null")) {
            return Opt.of(new NullValue());
        }
        return Opt.empty();
    }
}
