package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.reader.TextReader;
import lorikeet.error.NotAValidNumberToken;

public class NumberValueDeserializer implements ValueDeserializer<NumberValue> {
    @Override
    public Err<NumberValue> deserialize(TextReader text) {
        return text.nextNumber().map(NumberValue::new).asErr(new NotAValidNumberToken());
    }
}
