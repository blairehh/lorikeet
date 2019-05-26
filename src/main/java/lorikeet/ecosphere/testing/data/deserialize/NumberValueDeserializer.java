package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.reader.TextReader;

public class NumberValueDeserializer implements ValueDeserializer<NumberValue> {
    @Override
    public Opt<NumberValue> deserialize(TextReader text) {
        return text.nextNumber().map(NumberValue::new);
    }
}
