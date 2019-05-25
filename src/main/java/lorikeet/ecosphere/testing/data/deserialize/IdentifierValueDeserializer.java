package lorikeet.ecosphere.testing.data.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.IdentifierValue;
import lorikeet.ecosphere.testing.data.TextReader;

public class IdentifierValueDeserializer implements ValueDeserializer<IdentifierValue> {

    @Override
    public Opt<IdentifierValue> deserialize(TextReader reader) {
        return reader.nextIdentifier()
            .map(IdentifierValue::new)
            .toOpt();
    }
}
