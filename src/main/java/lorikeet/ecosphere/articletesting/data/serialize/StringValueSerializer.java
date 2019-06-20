package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.StringValue;
import lorikeet.ecosphere.articletesting.data.Value;

public class StringValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof StringValue)) {
            return Opt.empty();
        }
        return Opt.of(String.format("'%s'", ((StringValue)value).getValue()));
    }
}
