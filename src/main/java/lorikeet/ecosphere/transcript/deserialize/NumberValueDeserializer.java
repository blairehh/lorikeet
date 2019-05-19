package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.NumberValue;
import lorikeet.ecosphere.transcript.TextReader;

public class NumberValueDeserializer implements ValueDeserializer<NumberValue> {
    @Override
    public Opt<NumberValue> deserialize(TextReader text) {
        return text.nextNumber().map(NumberValue::new);
    }
}
