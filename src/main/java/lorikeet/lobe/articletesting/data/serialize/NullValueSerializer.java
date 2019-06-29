package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.NullValue;
import lorikeet.lobe.articletesting.data.Value;

public class NullValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof NullValue)) {
            return Opt.empty();
        }
        return Opt.of("null");
    }

}
