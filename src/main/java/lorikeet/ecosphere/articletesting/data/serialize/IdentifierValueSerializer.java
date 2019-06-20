package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.IdentifierValue;
import lorikeet.ecosphere.articletesting.data.Value;

public class IdentifierValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof IdentifierValue)) {
            return Opt.empty();
        }
        return Opt.of(((IdentifierValue)value).getIdentifier());
    }

}
