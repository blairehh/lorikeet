package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.HashValue;
import lorikeet.ecosphere.articletesting.data.Value;

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
