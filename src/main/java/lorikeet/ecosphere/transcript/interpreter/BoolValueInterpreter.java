package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.BoolValue;
import lorikeet.ecosphere.transcript.Value;

public class BoolValueInterpreter implements ValueInterpreter {

    @Override
    public Opt<Value> interpret(Object value) {
        if (value == null || !(value instanceof Boolean)) {
            return Opt.empty();
        }
        return Opt.of(new BoolValue((Boolean)value));
    }
}
