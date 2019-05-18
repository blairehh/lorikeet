package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.NullValue;
import lorikeet.ecosphere.transcript.TextReader;

public class NullDeserializer implements DatumDeserializer<NullValue> {
    public Opt<NullValue> deserialize(TextReader text) {
        final String token = text.nextToken();
        if (token.trim().equalsIgnoreCase("null")) {
            return Opt.of(new NullValue());
        }
        return Opt.empty();
    }
}
