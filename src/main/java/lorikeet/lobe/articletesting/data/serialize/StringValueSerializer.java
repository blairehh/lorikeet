package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.StringValue;
import lorikeet.lobe.articletesting.data.Value;

public class StringValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof StringValue)) {
            return Opt.empty();
        }
        return Opt.of(String.format("'%s'", ((StringValue)value).getValue()));
    }
}
