package lorikeet.ecosphere.testing.data.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.testing.data.StringValue;
import lorikeet.ecosphere.testing.data.Value;

public class StringValueInterpreter implements ValueInterpreter {


    @Override
    public Opt<Value> interpret(Object value) {
        if (value == null) {
            return Opt.empty();
        }

        if (!(value instanceof CharSequence) && !(value instanceof Character)) {
            return Opt.empty();
        }

        return Opt.of(new StringValue(value.toString()));
    }

}
