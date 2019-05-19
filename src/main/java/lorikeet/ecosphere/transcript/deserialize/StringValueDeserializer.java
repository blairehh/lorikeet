package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.StringValue;
import lorikeet.ecosphere.transcript.TextReader;

public class StringValueDeserializer implements ValueDeserializer<StringValue> {
    @Override
    public Opt<StringValue> deserialize(TextReader text) {
        return text.nextQuote('\'').map(StringValue::new).toOpt();
    }
}
