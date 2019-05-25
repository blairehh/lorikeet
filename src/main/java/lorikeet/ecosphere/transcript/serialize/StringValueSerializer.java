package lorikeet.ecosphere.transcript.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.StringValue;
import lorikeet.ecosphere.transcript.Value;

public class StringValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof StringValue)) {
            return Opt.empty();
        }
        return Opt.of(String.format("'%s'", ((StringValue)value).getValue()));
    }
}
