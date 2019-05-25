package lorikeet.ecosphere.testing.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.NumberValue;
import lorikeet.ecosphere.testing.data.Value;

public class NumberValueInterpreter implements ValueInterpreter {

    @Override
    public Opt<Value> interpret(Object object) {
        if (object == null || !(object instanceof Number)) {
            return Opt.empty();
        }
        return Opt.of(new NumberValue((Number)object));
    }
}
