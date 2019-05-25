package lorikeet.ecosphere.transcript.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.HashValue;
import lorikeet.ecosphere.transcript.Value;

public class HashValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value value) {
        if (!(value instanceof HashValue)) {
            return Opt.empty();
        }
        final HashValue hashValue = (HashValue)value;
        return Opt.of(String.format("%s#%s", hashValue.getClassName(), hashValue.getHashValue()));
    }

}
