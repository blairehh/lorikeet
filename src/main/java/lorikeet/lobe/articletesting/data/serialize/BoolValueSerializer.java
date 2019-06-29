package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.BoolValue;
import lorikeet.lobe.articletesting.data.Value;

public class BoolValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof BoolValue)) {
            return Opt.empty();
        }
        return Opt.of(String.valueOf(((BoolValue)value).getValue()));
    }

}
