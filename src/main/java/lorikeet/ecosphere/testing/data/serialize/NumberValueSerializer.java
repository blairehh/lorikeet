package lorikeet.ecosphere.testing.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.Value;

public class NumberValueSerializer implements ValueSerializer {

    @Override
    public Opt<String> serialize(Value number) {
        if (!(number instanceof NumberValue)) {
            return Opt.empty();
        }
        final Number value = ((NumberValue)number).getValue();
        if (value.doubleValue() % 1 == 0) {
            return Opt.of(String.valueOf(value.longValue()));
        }
        return Opt.of(String.valueOf(value.doubleValue()));
    }

}
