package lorikeet.ecosphere.testing.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.BoolValue;
import lorikeet.ecosphere.testing.data.Value;

public class BoolValueInterpreter implements ValueInterpreter {

    @Override
    public Opt<Value> interpret(Object value) {
        if (value == null || !(value instanceof Boolean)) {
            return Opt.empty();
        }
        return Opt.of(new BoolValue((Boolean)value));
    }
}
