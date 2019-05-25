package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.NumberValue;
import lorikeet.ecosphere.transcript.Value;

public class NumberValueInterpreter implements ValueInterpreter {

    @Override
    public Opt<Value> interpret(Object object) {
        if (object == null || !(object instanceof Number)) {
            return Opt.empty();
        }
        return Opt.of(new NumberValue((Number)object));
    }
}
