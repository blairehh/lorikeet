package lorikeet.ecosphere.transcript.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.BoolValue;
import lorikeet.ecosphere.transcript.Value;

public class BoolValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof BoolValue)) {
            return Opt.empty();
        }
        return Opt.of(String.valueOf(((BoolValue)value).getValue()));
    }

}
