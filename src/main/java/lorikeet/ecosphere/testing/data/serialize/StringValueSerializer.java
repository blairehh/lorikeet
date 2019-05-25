package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.data.Value;

public class StringValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof StringValue)) {
            return Opt.empty();
        }
        return Opt.of(String.format("'%s'", ((StringValue)value).getValue()));
    }
}
