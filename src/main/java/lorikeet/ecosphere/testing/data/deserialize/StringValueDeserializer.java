package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.reader.TextReader;

public class StringValueDeserializer implements ValueDeserializer<StringValue> {
    @Override
    public Err<StringValue> deserialize(TextReader text) {
        return text.nextQuote('\'').map(StringValue::new);
    }
}
