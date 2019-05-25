package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.TextReader;

public class BoolValueDeserializer implements ValueDeserializer<BoolValue> {
    @Override
    public Opt<BoolValue> deserialize(TextReader text) {
        final String token = text.nextWord().orElse("");
        if (token.trim().equalsIgnoreCase("true")) {
            return Opt.of(new BoolValue(true));
        }
        if (token.trim().equalsIgnoreCase("false")) {
            return Opt.of(new BoolValue(false));
        }
        return Opt.empty();
    }
}
