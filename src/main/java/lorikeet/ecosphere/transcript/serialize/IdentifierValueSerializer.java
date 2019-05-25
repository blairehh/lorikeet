package lorikeet.ecosphere.transcript.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.IdentifierValue;
import lorikeet.ecosphere.transcript.Value;

public class IdentifierValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof IdentifierValue)) {
            return Opt.empty();
        }
        return Opt.of(((IdentifierValue)value).getIdentifier());
    }

}
