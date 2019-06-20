package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.NullValue;
import lorikeet.ecosphere.articletesting.data.Value;

public class NullValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof NullValue)) {
            return Opt.empty();
        }
        return Opt.of("null");
    }

}
