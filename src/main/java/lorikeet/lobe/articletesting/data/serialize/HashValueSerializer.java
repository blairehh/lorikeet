package lorikeet.lobe.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.lobe.articletesting.data.HashValue;
import lorikeet.lobe.articletesting.data.Value;

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
