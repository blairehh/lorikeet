package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.NullValue;
import lorikeet.ecosphere.testing.data.Value;

public class NullValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof NullValue)) {
            return Opt.empty();
        }
        return Opt.of("null");
    }

}
