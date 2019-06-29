package lorikeet.lobe.articletesting.data.deserialize;

import lorikeet.Err;
import lorikeet.lobe.articletesting.data.NumberValue;
import lorikeet.lobe.articletesting.reader.TextReader;
import lorikeet.error.InconclusiveError;
import lorikeet.error.LorikeetException;
import lorikeet.error.NotAValidNumberToken;

public class NumberValueDeserializer implements ValueDeserializer<NumberValue> {

    private final boolean directDeserialization;

    public NumberValueDeserializer() {
        this.directDeserialization = true;
    }

    public NumberValueDeserializer(boolean directDeserialization) {
        this.directDeserialization = directDeserialization;
    }

    @Override
    public Err<NumberValue> deserialize(TextReader text) {
        final LorikeetException error = this.directDeserialization
            ? new NotAValidNumberToken()
            : new InconclusiveError(new NotAValidNumberToken());

        return text.nextNumber().map(NumberValue::new).asErr(error);
    }
}
