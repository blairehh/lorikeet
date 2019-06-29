package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.IdentifierValue;
import lorikeet.lobe.articletesting.data.Value;

public class IdentifierValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof IdentifierValue)) {
            return Opt.empty();
        }
        return Opt.of(((IdentifierValue)value).getIdentifier());
    }

}
