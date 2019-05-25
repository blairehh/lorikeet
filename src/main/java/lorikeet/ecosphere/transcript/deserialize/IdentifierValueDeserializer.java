package lorikeet.ecosphere.transcript.deserialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.IdentifierValue;
import lorikeet.ecosphere.transcript.TextReader;

public class IdentifierValueDeserializer implements ValueDeserializer<IdentifierValue> {

    @Override
    public Opt<IdentifierValue> deserialize(TextReader reader) {
        return reader.nextIdentifier()
            .map(IdentifierValue::new)
            .toOpt();
    }
}
