package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Err;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.reader.TextReader;

public class IdentifierValueDeserializer implements ValueDeserializer<IdentifierValue> {

    @Override
    public Err<IdentifierValue> deserialize(TextReader reader) {
        return reader.nextIdentifier()
            .map(IdentifierValue::new);
    }
}
