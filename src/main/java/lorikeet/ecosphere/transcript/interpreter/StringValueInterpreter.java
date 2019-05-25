package lorikeet.ecosphere.transcript.interpreter;

import lorikeet.Opt;
import lorikeet.ecosphere.transcript.StringValue;
import lorikeet.ecosphere.transcript.Value;

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
