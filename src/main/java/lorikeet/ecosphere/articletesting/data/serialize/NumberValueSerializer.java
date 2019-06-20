package lorikeet.ecosphere.articletesting.data.serialize;

import lorikeet.Opt;
import lorikeet.ecosphere.articletesting.data.NumberValue;
import lorikeet.ecosphere.articletesting.data.Value;

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
