package lorikeet.ecosphere.transcript.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.NullValue;
import lorikeet.ecosphere.transcript.Value;

public class NullValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof NullValue)) {
            return Opt.empty();
        }
        return Opt.of("null");
    }

}
