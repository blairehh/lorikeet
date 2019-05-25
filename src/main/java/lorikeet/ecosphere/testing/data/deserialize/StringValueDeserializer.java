package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.data.TextReader;

public class StringValueDeserializer implements ValueDeserializer<StringValue> {
    @Override
    public Opt<StringValue> deserialize(TextReader text) {
        return text.nextQuote('\'').map(StringValue::new).toOpt();
    }
}
