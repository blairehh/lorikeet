package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.Value;

public class BoolValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof BoolValue)) {
            return Opt.empty();
        }
        return Opt.of(String.valueOf(((BoolValue)value).getValue()));
    }

}
